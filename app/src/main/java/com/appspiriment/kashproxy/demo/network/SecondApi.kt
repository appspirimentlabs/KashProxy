package com.appspiriment.kashproxy.demo.network

import retrofit2.http.GET


/*********************************************************
 * Class   :  SecondApi
 * Author  :  Arun Nair
 * Created :  16/09/2022
 *******************************************************
 * Purpose :
 *******************************************************
 * Rework Details:
 * 1) {Author} :  {Date} : {Details}
 *********************************************************/

interface SecondApi {

    @GET("https://stackoverflow.com/api2/path1/path1-1.json")
    suspend fun path1(): String

    @GET("/api2/path2/path2-1.json")
    suspend fun path2(): String

    @GET("/api2/path3/path3-1.json")
    suspend fun path3(): String
}

