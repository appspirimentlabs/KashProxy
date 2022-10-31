package com.appspiriment.kashproxy.data.db

import androidx.core.text.isDigitsOnly
import com.appspiriment.kashproxy.ui.model.ResponseMappingModel

//https://stackoverflow.com/questions/44244508/room-persistance-library-delete-all?id=3w32423&ud=2432423
fun ResponseMappingModel.toEntity() = ResponseMappingEntity(
    mappingId = mappingId.takeIf { mappingId?.isDigitsOnly()==true }?.toInt(),
    apiUrl = url,
    protocol = protocol,
    apiHost = apiHost,
    path = path,
    queries = queries,
    isResponseStatusRewrite = isResponseStatusRewrite,
    isResponseMapping = isResponseMapping,
    responseStatusMap = HashMapTypeConverter.mapToString(responseStatusMap),
    responseBody = responseBody
)
fun ResponseMappingEntity.toModel() = ResponseMappingModel(
    mappingId = mappingId.toString(),
    url = apiUrl,
    protocol = protocol,
    apiHost = apiHost,
    path = path,
    queries = queries,
    isResponseStatusRewrite = isResponseStatusRewrite,
    isResponseMapping = isResponseMapping,
    responseStatusMap = HashMapTypeConverter.stringToMap(responseStatusMap),
    responseBody = responseBody
)