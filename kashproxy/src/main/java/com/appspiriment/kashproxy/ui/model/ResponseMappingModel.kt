package com.appspiriment.kashproxy.ui.model

import android.net.Uri
import com.google.gson.Gson
import java.net.URL


/*********************************************************
 * Class   :  ResponseProxyModel
 * Author  :  Arun Nair
 * Created :  28/10/2022
 *******************************************************
 * Purpose : Model class for each proxy rewrite setting
 *******************************************************
 * Rework Details:
 * 1) {Author} :  {Date} : {Details}
 *********************************************************/
internal data class ResponseMappingModel(
    val url: String,
    val protocol: String,
    val apiHost: String,
    val path: String?,
    val queries: String?,
    var mapToSuccess: Boolean,
    val mappingNickName: String?,
    val httpCode: Int,
    val successResponse: String?,
    val errorResponse: String?,
    var mappingEnabled: Boolean
){
    fun toJson() = Gson().toJson(this)

    companion object{
        fun fromJson(json:String) = Gson().fromJson(json, ResponseMappingModel::class.java)
    }
}
