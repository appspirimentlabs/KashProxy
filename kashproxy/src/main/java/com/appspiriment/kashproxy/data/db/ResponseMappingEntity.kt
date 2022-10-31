package com.appspiriment.kashproxy.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey


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
@Entity(tableName = "responseMapping")
data class ResponseMappingEntity(
    @PrimaryKey(autoGenerate= true) val mappingId: Int?,
    val apiUrl: String,
    val protocol: String,
    val apiHost: String,
    val path: String?,
    val queries: String?,
    val isResponseStatusRewrite: Boolean,
    val isResponseMapping: Boolean,
    val responseStatusMap: String?,
    val responseBody: String?
)
