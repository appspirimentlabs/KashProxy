package com.appspiriment.kashproxy.demo.network

import android.util.Log
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.URI

class NetworkRepository(private val networkModule: NetworkModule) {

    inner class ErrorParser(
        val code: String,
        val message: String
    )

    suspend fun getApiResult(url: String?): ApiResult {
        return try {
            ApiResult(
                status = 200,
                data = getApiResultSafely(url),
                error = null
            )
        } catch (ex: HttpException) {
            try {
                ex.response()?.errorBody()?.string()?.let {
                        ApiResult(
                            status = ex.code(),
                            data = JSONObject(it).toString(4),
                            error = null
                        )
                } ?: run{throw ex}
            } catch (e: IOException) {
                ApiResult(
                    status = 500,
                    data = e.message,
                    error = null
                )
            }

        } catch (ex1: Exception) {
            ApiResult(
                status = 500,
                data = ex1.message,
                error = null
            )
        }
    }

    suspend fun getApiResultSafely(url: String?): String {
        return if (!url.isNullOrBlank()) {
            val urlFormat = URI.create(url)
            val urlPath = urlFormat.path.trimStart { it == '/' }
            Log.e("Log2", urlPath)
            val queries = urlFormat.query.split("&")
            val queryMap = LinkedHashMap<String, String>().apply {
                queries.forEach { query ->
                    query.split("=").let {
                        if (it.size == 2) {
                            put(it[0], it[1])
                        }
                    }
                }
            }
            networkModule.getNetworkApi(urlFormat).getUrlPathResult(urlPath, queryMap)
        } else throw Exception("Not valid host")
    }
}