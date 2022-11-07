package com.appspiriment.kashproxy.data.db

import com.appspiriment.kashproxy.data.repository.LocalDataSource
import com.appspiriment.kashproxy.ui.model.ResponseMappingModel

class LocalRoomDataSource(private val dao: ResponseMappingDao) : LocalDataSource {
    override suspend fun insertProxyMapping(mapping: ResponseMappingModel) {
        dao.insertProxyMapping(mapping.toEntity())
    }

    override suspend fun deleteProxyMapping(url: String) {
        dao.deleteProxyMapping(url)
    }

    override suspend fun getAllMapping() = dao.getAllMapping().map{ it.toModel()}

    override suspend fun getMappingByUrl(url: String) = dao.getMappingByUrl(url)?.toModel()

    override suspend fun getMappingByHost(
        protocol: String,
        host: String
    ) = dao.getMappingByHost(protocol, host).map{ it.toModel() }
}