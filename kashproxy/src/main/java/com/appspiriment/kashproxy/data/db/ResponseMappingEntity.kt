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
    @PrimaryKey val apiUrl: String,
    val protocol: String,
    val apiHost: String,
    val path: String?,
    val queries: String?,
    val mapToSuccess: Boolean,
    val httpCode: Int,
    val mappingNickName: String?,
    val successResponse: String?,
    val errorResponse: String?,
    val mappingEnabled: Boolean
)
