package com.appspiriment.kashproxy.api

import android.content.Context
import com.chuckerteam.chucker.api.Chucker
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.chuckerteam.chucker.api.RetentionManager.*
import okhttp3.Interceptor


object KashProxy {

    /**
     * This method will get an instance of KashProxyInterceptor which is an OkHttp Interceptor which will enable the KashProxy mapping and Chucker
     * @param context An Android [Context].
     * @param showNotification Whether to show the Chucker Notifications (KashProxy notifications are always enabled). default true[Boolean] ,
     * @param retentionPeriod How long to retain the collected HTTP transactions data in Chucker. default ONE_HOUR [RetentionManager.Period] ,
     * @param maxContentLength The max body content length in bytes, after this responses will be truncated. default ONE_HOUR [RetentionManager.Period] ,
     * @param headersToRedact List of headers to replace with ** in the Chucker UI. [Iterable] ,
     * @param alwaysReadResponseBody Read the whole response body even when the client does not consume the response completely. This is useful in case of parsing errors or when the response body is closed before being read like in Retrofit with Void and Unit types. [Boolean] ,
     * @return An instance of OkHTTP Interceptor [Interceptor].
     */
    public fun getInterceptor(
        context: Context,
        showNotification: Boolean = true,
        retentionPeriod: RetentionManager.Period = RetentionManager.Period.ONE_HOUR,
        maxContentLength: Long = 250000L,
        headersToRedact: Iterable<String> = emptyList(),
        alwaysReadResponseBody: Boolean = false
    ): Interceptor {

        // Create the Collector
        val chuckerCollector = ChuckerCollector(
            context = context,
            // Toggles visibility of the notification
            showNotification = showNotification,
            // Allows to customize the retention period of collected data
            retentionPeriod = retentionPeriod,
        )


        // Create the Interceptor
        return Builder(context).apply {
            // The previously created Collector
            collector(chuckerCollector)
            // The max body content length in bytes, after this responses will be truncated
            maxContentLength(maxContentLength)
            // List of headers to replace with ** in the Chucker UI
            redactHeaders(headersToRedact)
            // Read the whole response body even when the client does not consume the response completely
            // This is useful in case of parsing errors or when the response body
            // is closed before being read like in Retrofit with Void and Unit types
            alwaysReadResponseBody(alwaysReadResponseBody)
        }.build()
    }

    /**
     * Assembles a new [KashProxyInterceptor].
     *
     * @param context An Android [Context].
     */
    class Builder(context: Context) : ChuckerInterceptor.Builder(context)


    /**
     * Launches the KashProxy UI directly.
     * @param context An Android [Context].
     */
    fun showMappingActivity(context: Context) = Chucker.showMappingActivity(context)


    /**
     * Launches the Chucker UI directly.
     * @param context An Android [Context].
     */
    public fun showChuckerActivity(context: Context) = Chucker.showChuckerActivity(context)

    /**
     * Get an Intent to launch the KashProxy Mapping UI directly.
     * @param context An Android [Context].
     * @return An Intent for the main KashProxy Mapping Activity that can be started with [Context.startActivity].
     */
    fun getMappingLaunchIntent(context: Context) = Chucker.getMappingLaunchIntent(context)

    /**
     * Get an Intent to launch the Chucker UI directly.
     * @param context An Android [Context].
     * @return An Intent for the main Chucker Activity that can be started with [Context.startActivity].
     */
    fun getChuckerLaunchIntent(context: Context) = Chucker.getLaunchIntent(context)


    /**
     * Changes whether the response mapping is enabled or not.
     * @param context An Android [Context].
     * @param enabled Response mapping is enabled [Boolean]
     */
    fun enableKashProxyMapping(context: Context, enabled: Boolean) =
        Chucker.enableKashProxyMapping(context, enabled)

    /**
     * Checks whether the response mapping is enabled or not.
     * @param context An Android [Context].
     * @return Boolean value representing whether the response mapping is enabled[Boolean]
     */
    fun isKashProxyMappingEnabled(context: Context) = Chucker.isKashProxyMappingEnabled(context)

    /**
     * Dismisses all previous Chucker notifications.
     */
    @JvmStatic
    public fun dismissNotifications(context: Context) {
        Chucker.dismissNotifications(context)
    }
}