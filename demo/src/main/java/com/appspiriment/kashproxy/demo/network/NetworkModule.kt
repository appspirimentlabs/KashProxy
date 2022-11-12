package com.appspiriment.kashproxy.demo.network

import android.content.Context
import android.util.Log
import com.appspiriment.kashproxy.api.KashProxy
import com.appspiriment.kashproxy.demo.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.ConnectionSpec
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import java.net.URI
import java.net.URL
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
class NetworkModule(val context: Context) {


    fun getRetrofit(baseurl: String): Retrofit {
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
            KashProxy.getInterceptors(context).forEach {
                addInterceptor(it)
            }
            readTimeout(100, TimeUnit.SECONDS).build()
            cache(cache)
        }

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()

        return Retrofit.Builder().client(client.build())
            .baseUrl(baseurl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getNetworkApi(uri: URI): NetworkApi {
        Log.e("Log", "${uri.toURL().protocol}://${uri.toURL().host}")
        return getRetrofit("${uri.toURL().protocol}://${uri.toURL().host}").also {
            Log.e("Log3", it.baseUrl().toString())
        }.create(NetworkApi::class.java)
    }

}

