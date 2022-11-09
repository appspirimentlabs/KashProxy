package com.appspiriment.kashproxy.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class KashProxyInterceptor internal constructor(val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(request)
    }
}