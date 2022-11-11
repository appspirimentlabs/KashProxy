package com.appspiriment.kashproxy.demo.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap


/*********************************************************
 * Class   :  ThirdApi
 * Author  :  Arun Nair
 * Created :  16/09/2022
 *******************************************************
 * Purpose :
 *******************************************************
 * Rework Details:
 * 1) {Author} :  {Date} : {Details}
 *********************************************************/

interface NetworkApi {

    @GET("/{path}")
    suspend fun getUrlPathResult(
        @Path(value = "path", encoded = true) path: String,
        @QueryMap(encoded = true) options: Map<String, String>
    ): String

}
