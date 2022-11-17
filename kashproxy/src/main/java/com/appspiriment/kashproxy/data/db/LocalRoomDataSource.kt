package com.appspiriment.kashproxy.data.db

import com.appspiriment.kashproxy.data.repository.LocalDataSource
import com.appspiriment.kashproxy.ui.model.ResponseMappingModel
import java.net.URI

internal class LocalRoomDataSource(private val dao: ResponseMappingDao) : LocalDataSource {
    override suspend fun insertProxyMapping(mapping: ResponseMappingModel) {
        dao.insertProxyMapping(mapping.toEntity())
    }

    override suspend fun deleteProxyMapping(mappingId: Int) {
        dao.deleteProxyMapping(mappingId)
    }

    override suspend fun getAllMapping() = dao.getAllMapping().map { it.toModel() }

    override suspend fun getMappingByUrl(url: String): ResponseMappingModel? {
        val uri = URI.create(url)
        val protocol = uri.toURL().protocol
        val host = uri.host
        val path = uri.path

        //Check for mappings matching the whole URL
        dao.getEnabledMappingByUrl(url)?.toModel()?.let {
            return it
        }

        //Check for mapping with ordered queries
        dao.getMappingByHostPathAndQueries(
            protocol = protocol,
            host = host,
            path = "${path}%",
            queries = "${uri.query.split("&").sorted().joinToString(separator = "&")}%"
        )?.toModel()?.let {
            return it
        }

        //Check for mapping for each path segments recursively
        path.trim('/').split("/").let { paths ->
            val pathSegments = arrayListOf<String>().apply { addAll(paths) }
            for (i in paths.indices) {
                dao.getMappingByHostPath(
                    protocol = protocol,
                    host = host,
                    path = "/" + pathSegments.joinToString("/")
                )?.let {
                    return it.toModel()
                }
                pathSegments.removeLast()
            }
        }

        //Check for mapping with host alone
        return dao.getMappingByHost(
            protocol = protocol,
            host = host
        )?.toModel()

    }

    override suspend fun getMappingById(mappingId: Int) =
        dao.getMappingById(mappingId)?.toModel()

}
