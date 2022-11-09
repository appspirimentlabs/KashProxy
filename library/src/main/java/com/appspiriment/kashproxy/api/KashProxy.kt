package com.appspiriment.kashproxy.api

import android.content.Context
import android.content.Intent
import com.chuckerteam.chucker.api.BodyDecoder
import com.chuckerteam.chucker.api.RetentionManager.*
import okhttp3.Interceptor
import com.chuckerteam.chucker.api.Chucker
import com.appspiriment.kashproxy.di.KashProxyLib
import com.appspiriment.kashproxy.network.KashProxyInterceptor


object KashProxy {

    public fun getInterceptors(
        context: Context,
        showNotification: Boolean = true,
        retentionPeriod: Period = Period.ONE_HOUR,
        bodyDecoders: List<BodyDecoder> = emptyList()
    ): List<Interceptor> {
        return listOf(
            getChuckerInterceptor(context, showNotification, retentionPeriod, bodyDecoders),
            getKashProxyInterceptor(context)
        )
    }

    fun getChuckerInterceptor(
        context: Context, showNotification: Boolean = true,
        retentionPeriod: Period = Period.ONE_HOUR,
        bodyDecoders: List<BodyDecoder> = emptyList()
    ): Interceptor {
        return Chucker.getChuckerInterceptor(
            context,
            showNotification,
            retentionPeriod,
            bodyDecoders
        )
    }

    fun getKashProxyInterceptor(context: Context): KashProxyInterceptor {
        return KashProxyLib.getKashProxyInterceptor(context)
    }

    fun showMappingActivity(context: Context) {
        KashProxyLib.showMappingActivity(context)
    }

    public fun showChuckerActivity(context: Context) {
        Chucker.getLaunchIntent(context).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }.let {
            context.startActivity(it)
        }
    }

    fun enableKashProxyMapping(context: Context, enabled: Boolean) =
        KashProxyLib.enableKashProxyMapping(context, enabled)

    fun isKashProxyMappingEnabled(context: Context) = KashProxyLib.isKashProxyMapping(context)

    @JvmStatic
    public fun dismissNotifications(context: Context) {
        Chucker.dismissNotifications(context)
    }
}