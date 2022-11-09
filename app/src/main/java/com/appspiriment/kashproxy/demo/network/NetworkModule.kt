package com.appspiriment.kashproxy.demo.network

import android.content.Context
import com.appspiriment.kashproxy.api.KashProxy
import com.appspiriment.kashproxy.demo.BuildConfig
import com.appspiriment.kashproxy.demo.di.KashProxyDemoApp
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


/*********************************************************
 * Class   :  DataLocalModule
 * Author  :  Arun Nair
 * Created :  15/09/2022
 *******************************************************
 * Purpose :
 *******************************************************
 * Rework Details:
 * 1) {Author} :  {Date} : {Details}
 *********************************************************/

/***************************************
 * Declarations
 ***************************************/
class NetworkModule(context: Context) {
    val retrofit by lazy { getRetrofit(context) }

    val firstApi by lazy { retrofit.create(FirstApi::class.java)}
    val secondApi by lazy { retrofit.create(SecondApi::class.java)}
    val thirdApi by lazy { retrofit.create(ThirdApi::class.java)}

    fun getRetrofit(context: Context): Retrofit {
        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val cache = Cache(context.cacheDir, cacheSize)
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            client.addNetworkInterceptor(logging)
        }
        client.connectionSpecs(
            listOf(
                ConnectionSpec.MODERN_TLS,
                ConnectionSpec.COMPATIBLE_TLS,
                ConnectionSpec.CLEARTEXT
            )
        ).apply {

            connectTimeout(100, TimeUnit.SECONDS)
            KashProxy.getInterceptors(context).forEach{
                addInterceptor(it)
            }
            readTimeout(100, TimeUnit.SECONDS).build()
            cache(cache)
        }

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()

        return Retrofit.Builder().client(client.build())
            .baseUrl(KashProxyDemoApp.baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

}