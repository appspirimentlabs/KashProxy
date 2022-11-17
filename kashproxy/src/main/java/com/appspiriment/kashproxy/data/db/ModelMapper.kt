package com.appspiriment.kashproxy.data.db

import com.appspiriment.kashproxy.ui.model.ResponseMappingModel

internal fun ResponseMappingModel.toEntity() = ResponseMappingEntity(
    mappingId = mappingId,
    apiUrl = url,
    protocol = protocol,
    apiHost = apiHost,
    path = path,
    queries = queries,
    mapToSuccess = mapToSuccess,
    mappingNickName = mappingNickName,
    httpCode = httpCode,
    successResponse = successResponse,
    errorResponse = errorResponse,
    mappingEnabled = mappingEnabled
)
internal fun ResponseMappingEntity.toModel() = ResponseMappingModel(
    mappingId = mappingId,
    url = apiUrl,
    protocol = protocol,
    apiHost = apiHost,
    path = path,
    queries = queries,
    mapToSuccess = mapToSuccess,
    mappingNickName = mappingNickName,
    httpCode = httpCode,
    successResponse = successResponse,
    errorResponse = errorResponse,
    mappingEnabled = mappingEnabled
)
