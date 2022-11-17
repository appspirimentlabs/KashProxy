package com.appspiriment.kashproxy.data.repository

import com.appspiriment.kashproxy.ui.model.ResponseMappingModel

internal class ResponseMappingRepository(val localDataSource: LocalDataSource) {

    suspend fun insertProxyMapping(mapping: ResponseMappingModel) =
        localDataSource.insertProxyMapping(mapping)

    suspend fun deleteProxyMapping(mappingId: Int?) {
        mappingId?.let {
            localDataSource.deleteProxyMapping(mappingId)
        }
    }

    suspend fun getAllMapping() = localDataSource.getAllMapping()

    suspend fun getMappingByUrl(url: String?): ResponseMappingModel? {
        return url?.let {
            localDataSource.getMappingByUrl(it)
        }
    }

    suspend fun getMappingById(mappingId: Int?): ResponseMappingModel? {
        return mappingId?.let {
            localDataSource.getMappingById(it)
        }
    }
}
