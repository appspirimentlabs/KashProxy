package com.appspiriment.kashproxy.api

import android.content.Context
import com.appspiriment.kashproxy.network.KashProxyInterceptor
import com.chuckerteam.chucker.api.RetentionManager.Period
import okhttp3.Interceptor


object KashProxy {

    public fun getInterceptors(
        context: Context,
        showNotification: Boolean = true,
        retentionPeriod: Period = Period.ONE_HOUR,
        bodyDecoders: List<Any> = emptyList()
    ) = emptyList<Interceptor>()

    fun getChuckerInterceptor(
        context: Context, showNotification: Boolean = true,
        retentionPeriod: Period = Period.ONE_HOUR,
        bodyDecoders: List<Any> = emptyList()
    ) = KashProxyInterceptor(context)

    fun getKashProxyInterceptor(context: Context) = KashProxyInterceptor(context)

    fun showMappingActivity(context: Context) { /* Do Nothing */ }

    public fun showChuckerActivity(context: Context) { /* Do Nothing */ }

    fun enableKashProxyMapping(context: Context, enabled: Boolean) { /* Do Nothing */ }

    fun isKashProxyMappingEnabled(context: Context) = false

    @JvmStatic
    public fun dismissNotifications(context: Context){ /* Do Nothing */ }
}