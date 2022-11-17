package com.appspiriment.kashproxy.api

import android.content.Context
import android.content.Intent
import com.chuckerteam.chucker.api.RetentionManager
import com.chuckerteam.chucker.api.RetentionManager.*
import com.chuckerteam.chucker.api.ChuckerInterceptor
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
     * @return An instance of ChuckerInterceptor which performs nothing[Interceptor].
     */
    public fun getInterceptor(
        context: Context,
        showNotification: Boolean = true,
        retentionPeriod: RetentionManager.Period = RetentionManager.Period.ONE_HOUR,
        maxContentLength: Long = 250000L,
        headersToRedact: Iterable<String> = emptyList(),
        alwaysReadResponseBody: Boolean = false
    ): Interceptor = Builder(context).build()

    /**
     * Assembles a new [KashProxyInterceptor].
     *
     * @param context An Android [Context].
     */
    class Builder(context: Context) : ChuckerInterceptor.Builder(context)


    /**
     * Launches the KashProxy UI directly.
     * Empty method for the library-no-op artifact
     * @param context An Android [Context].
     */
    fun showMappingActivity(context: Context) {
        // Empty method for the library-no-op artifact
    }


    /**
     * Launches the Chucker UI directly.
     * Empty method for the library-no-op artifact
     * @param context An Android [Context].
     */
    public fun showChuckerActivity(context: Context) {
        // Empty method for the library-no-op artifact
    }

    /**
     * Get an Intent to launch the KashProxy Mapping UI directly.
     * @param context An Android [Context].
     * @return An empty Intent for the library-no-op variant
     */
    fun getMappingLaunchIntent(context: Context)  = Intent()

    /**
     * Get an Intent to launch the Chucker UI directly.
     * Empty method for the library-no-op artifact
     * @param context An Android [Context].
     * @return An empty Intent for the library-no-op variant
     */
    fun getChuckerLaunchIntent(context: Context)  = Intent()


    /**
     * Changes whether the response mapping is enabled or not.
     * Empty method for the library-no-op artifact
     * @param context An Android [Context].
     * @param enabled Response mapping is enabled [Boolean]
     */
    fun enableKashProxyMapping(context: Context, enabled: Boolean) {
        // Empty method for the library-no-op artifact
    }

    /**
     * Checks whether the response mapping is enabled or not.
     * @param context An Android [Context].
     * @return Always return false for the library-no-op variant
     */
    fun isKashProxyMappingEnabled(context: Context)= false

    /**
     * Dismisses all previous Chucker notifications.
     * Empty method for the library-no-op artifact
     */
    @JvmStatic
    public fun dismissNotifications(context: Context) {
        // Empty method for the library-no-op artifact
    }
}