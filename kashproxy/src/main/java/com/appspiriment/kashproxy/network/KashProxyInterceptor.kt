package com.appspiriment.kashproxy.network

import android.content.Context
import com.appspiriment.kashproxy.data.preference.isKashProxyMappingEnabled
import com.appspiriment.kashproxy.di.KashProxyApp
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class KashProxyInterceptor internal constructor(val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        return try {
            getMappedResponse(chain.proceed(builder.build()))
        } catch (e: Exception) {
            if (context.isKashProxyMappingEnabled()) {
                Response.Builder().apply {
                    protocol(if(chain.request().isHttps) Protocol.HTTP_1_1 else Protocol.HTTP_2)
                    request(chain.request())
                    code(500)
                    message(e.message?:"")
                    body(ResponseBody.create(null, "{${e.message}}"))
                }.build().let {
                    getMappedResponse(it)
                }
            } else throw e
        }
    }


    private fun getMappedResponse(apiResponse: Response): Response {

        return if (context.isKashProxyMappingEnabled()) {
            runBlocking {
                apiResponse.let {
                    Response.Builder().apply {
                        code(it.code)
                        body(it.body)
                        message(it.message)
                        request(it.request)
                        protocol(it.protocol)
                        handshake(it.handshake)
                        headers(it.headers)
                        networkResponse(it.networkResponse)
                        cacheResponse(it.cacheResponse)
                        priorResponse(it.priorResponse)
                        sentRequestAtMillis(it.sentRequestAtMillis)
                        receivedResponseAtMillis(it.receivedResponseAtMillis)

                        KashProxyApp
                            .getMappingRepository()
                            .getMappingByUrl(it.request.url.toString())?.let{ mapping ->
                                if(mapping.mappingEnabled){
                                    if(mapping.mapToSuccess){
                                        body(ResponseBody.create(null, mapping.successResponse?:""))
                                        message(mapping.successResponse?:"")
                                        code(200)
                                    } else {
                                        body(ResponseBody.create(null, mapping.errorResponse?:""))
                                        message(mapping.errorResponse?:"")
                                        code(mapping.httpCode)
                                    }
                                }
                            }
                    }.build()

                }
            }
        } else {
            apiResponse
        }
    }
}