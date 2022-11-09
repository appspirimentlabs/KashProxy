package com.appspiriment.kashproxy.data.repository

import com.appspiriment.kashproxy.data.db.ResponseMappingEntity
import com.appspiriment.kashproxy.ui.model.ResponseMappingModel
import okhttp3.Protocol

internal interface LocalDataSource {

    suspend fun insertProxyMapping(mapping: ResponseMappingModel)

    suspend fun deleteProxyMapping(url: String)

    suspend fun getAllMapping(): List<ResponseMappingModel>

    suspend fun getMappingByUrl(url: String): ResponseMappingModel?

    suspend fun getMappingByHost(
        protocol: String,
        host: String
    ): List<ResponseMappingModel>
}