package com.appspiriment.kashproxy.data.repository

import androidx.core.text.isDigitsOnly
import com.appspiriment.kashproxy.ui.model.ResponseMappingModel
import java.net.URI

class ResponseMappingRepository(val localDataSource: LocalDataSource) {

    suspend fun insertProxyMapping(mapping: ResponseMappingModel) =
        localDataSource.insertProxyMapping(mapping)

    suspend fun deleteProxyMapping(url: String?) {
        url?.let {
            localDataSource.deleteProxyMapping(it)
        }
    }

    suspend fun getAllMapping() = localDataSource.getAllMapping()

    suspend fun getMappingByUrl(url: String?): ResponseMappingModel? {
        return url?.let {
            localDataSource.getMappingByUrl(it)
        }
    }

    suspend fun getMappingByHost(url: String): List<ResponseMappingModel> {
        val urlObj = URI.create(url).toURL()
        val protocol = urlObj.protocol
        val host = urlObj.host
        val path = urlObj.path.takeUnless { it.isNullOrBlank() }
        val queries = urlObj.query.takeUnless { it.isNullOrBlank() }

        val hostMappings = localDataSource.getMappingByHost(protocol, host)
//            check for mapping by host alone
        val hostAloneMappings =
            hostMappings.filter { it.path.isNullOrBlank() && it.queries.isNullOrBlank() }

        return hostAloneMappings.ifEmpty {

            return hostMappings.filter {
                it.path?.startsWith(
                    path ?: ""
                ) == true && (path == null || it.queries.isNullOrBlank())
            }.ifEmpty {
                emptyList()
            }
        }
    }
}