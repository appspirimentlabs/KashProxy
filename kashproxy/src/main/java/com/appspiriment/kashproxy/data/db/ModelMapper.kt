package com.appspiriment.kashproxy.data.db

import com.appspiriment.kashproxy.ui.model.ResponseMappingModel

fun ResponseMappingModel.toEntity() = ResponseMappingEntity(
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
fun ResponseMappingEntity.toModel() = ResponseMappingModel(
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