package com.appspiriment.kashproxy.data.db

import com.appspiriment.kashproxy.data.repository.LocalDataSource
import com.appspiriment.kashproxy.ui.model.ResponseMappingModel

class LocalRoomDataSource(private val dao: ResponseMappingDao) : LocalDataSource {
    override suspend fun insertProxyMapping(mapping: ResponseMappingModel) {
        dao.insertProxyMapping(mapping.toEntity())
    }

    override suspend fun deleteProxyMapping(id: Int) {
        dao.deleteProxyMapping(id)
    }

    override suspend fun getAllMapping() = dao.getAllMapping().map{ it.toModel()}

    override suspend fun getMappingById(id: Int) = dao.getMappingById(id).toModel()

    override suspend fun getMappingByHost(
        protocol: String,
        host: String
    ) = dao.getMappingByHost(protocol, host).map{ it.toModel() }
}