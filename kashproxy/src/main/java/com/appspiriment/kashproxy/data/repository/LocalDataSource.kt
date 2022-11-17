package com.appspiriment.kashproxy.data.repository

import com.appspiriment.kashproxy.ui.model.ResponseMappingModel

internal interface LocalDataSource {

    suspend fun insertProxyMapping(mapping: ResponseMappingModel)

    suspend fun deleteProxyMapping(mappingId: Int)

    suspend fun getAllMapping(): List<ResponseMappingModel>

    suspend fun getMappingByUrl(url: String): ResponseMappingModel?

    suspend fun getMappingById(mappingId: Int): ResponseMappingModel?

}
