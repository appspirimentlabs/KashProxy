package com.appspiriment.kashproxy.network

import android.content.Context
import com.appspiriment.kashproxy.data.preference.isKashProxyMappingEnabled
import com.appspiriment.kashproxy.di.KashProxy
import com.appspiriment.kashproxy.utils.NotificationUtils
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

        return try {
            checkAndMapResponse(chain.request()) ?: run {
                chain.proceed(chain.request().newBuilder().build())
            }
        } catch (e: Exception) {
            checkAndMapResponse(chain.request()) ?: run { throw e }
        }
    }


    private fun checkAndMapResponse(request: Request): Response? {
        return if (context.isKashProxyMappingEnabled()) {
            runBlocking {
                getMappedResponse(request)
            }
        } else null
    }

    private suspend fun getMappedResponse(request: Request): Response? {
        return KashProxy
            .getMappingRepository()
            .getMappingByUrl(request.url.toString())?.let { mapping ->
                if (mapping.mappingEnabled) {
                    Response.Builder().apply {
                        request(request)
                        protocol(if (request.isHttps) Protocol.HTTP_1_1 else Protocol.HTTP_2)
                        headers(request.headers)
                        if (mapping.mapToSuccess) {
                            body(ResponseBody.create(null, mapping.successResponse ?: ""))
                            message(mapping.successResponse ?: "")
                            code(200)
                        } else {
                            body(ResponseBody.create(null, mapping.errorResponse ?: ""))
                            message(mapping.errorResponse ?: "")
                            code(mapping.httpCode)
                        }
                        NotificationUtils(context).displayMappingTx(
                            mapping.path?.takeIf { it.isNotBlank() } ?: mapping.apiHost,
                            if (mapping.mapToSuccess) "200" else mapping.httpCode.toString()
                        )

                    }.build()
                } else null
            }
    }
}