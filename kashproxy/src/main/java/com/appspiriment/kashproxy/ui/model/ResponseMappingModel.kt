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
data class ResponseMappingModel(
    val mappingId: String?,
    val url: String,
    val protocol: String,
    val apiHost: String,
    val path: String?,
    val queries: String?,
    val isResponseStatusRewrite: Boolean,
    val isResponseMapping: Boolean,
    val responseStatusMap: HashMap<Int, Pair<Int, Boolean>>,
    val responseBody: String?
){
    fun toJson() = Gson().toJson(this)

    companion object{
        fun fromJson(json:String) = Gson().fromJson(json, ResponseMappingModel::class.java)
    }
}
