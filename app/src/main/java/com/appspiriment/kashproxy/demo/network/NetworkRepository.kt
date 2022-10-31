package com.appspiriment.kashproxy.demo.network

import com.appspiriment.kashproxy.demo.di.KashProxyDemoApp
import retrofit2.HttpException

class NetworkRepository(private val networkModule: NetworkModule) {

    suspend fun getApiResult(host: String?, path: String?): ApiResult {
        return try {
            ApiResult(
                status = 200,
                data = getApiResultSafely(host, path),
                error = null
            )
        } catch (ex: HttpException) {
            ApiResult(
                status = ex.code(),
                data = null,
                error = ex
            )
        } catch (ex1: Exception) {
            ApiResult(
                status = 500,
                data = null,
                error = ex1
            )
        }
    }

    fun getApiUrl(host: String?, path: String?): String{
        return when (host) {
            "digithreads.com" -> return "http://digithreads.com/api1/path1/path1-13.json"
            "Api 1" -> {
                return when (path) {
                    "Path 1" -> "${KashProxyDemoApp.baseUrl}/api1/path1/path1-1.json"
                    "Path 2" -> "${KashProxyDemoApp.baseUrl}/api1/path2/path2-1.json"
                    "Path 3" -> "${KashProxyDemoApp.baseUrl}/api1/path3/path3-1.json"
                    else -> ""
                }
            }

            "Api 2" -> {
                return when (path) {
                    "Path 1" -> "${KashProxyDemoApp.baseUrl}/api2/path1/path1-1.json"
                    "Path 2" -> "${KashProxyDemoApp.baseUrl}/api2/path2/path2-1.json"
                    "Path 3" -> "${KashProxyDemoApp.baseUrl}/api2/path3/path3-1.json"
                    else -> ""
                }
            }

            "Api 3" -> {
                return when (path) {
                    "Path 1" -> "${KashProxyDemoApp.baseUrl}/api3/path1/path1-1.json"
                    "Path 2" -> "${KashProxyDemoApp.baseUrl}/api3/path2/path2-1.json"
                    "Path 3" -> "${KashProxyDemoApp.baseUrl}/api3/path3/path3-1.json"
                    else -> ""
                }
            }

            else -> ""
        }
    }

    suspend fun getApiResultSafely(host: String?, path: String?): String {
        return when (host) {
            "digithreads.com" -> return networkModule.firstApi.digithreads()
            "Api 1" -> {
                return when (path) {
                    "Path 1" -> networkModule.firstApi.path1()
                    "Path 2" -> networkModule.firstApi.path2()
                    "Path 3" -> networkModule.firstApi.path3()
                    else -> "Not valid path"
                }
            }

            "Api 2" -> {
                return when (path) {
                    "Path 1" -> networkModule.secondApi.path1()
                    "Path 2" -> networkModule.secondApi.path2()
                    "Path 3" -> networkModule.secondApi.path3()
                    else -> ""
                }
            }

            "Api 3" -> {
                return when (path) {
                    "Path 1" -> networkModule.thirdApi.path1()
                    "Path 2" -> networkModule.thirdApi.path2()
                    "Path 3" -> networkModule.thirdApi.path3()
                    else -> ""
                }
            }

            else -> "Not valid host"

        }
    }
}