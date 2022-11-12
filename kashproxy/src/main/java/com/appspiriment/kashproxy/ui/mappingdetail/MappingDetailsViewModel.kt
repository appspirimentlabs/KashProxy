package com.appspiriment.kashproxy.ui.mappingdetail

import android.app.Application
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.appspiriment.kashproxy.R
import com.appspiriment.kashproxy.di.KashProxyLib
import com.appspiriment.kashproxy.ui.model.MapUrlModel
import com.appspiriment.kashproxy.ui.model.MappingItem
import com.appspiriment.kashproxy.ui.model.ResponseMappingModel
import com.appspiriment.kashproxy.utils.baseclasses.BaseAndroidViewModel
import com.appspiriment.kashproxy.utils.events.SingleLiveData
import com.appspiriment.kashproxy.utils.extentions.formatToJson
import kotlinx.coroutines.launch
import java.net.URI

internal class MappingDetailsViewModel(
    application: Application, private val url: String?, private val mapUrlModel: MapUrlModel?
) : BaseAndroidViewModel(application) {

    val apiUrlError = MutableLiveData<Int?>()

    val mappingEnabled = MutableLiveData(true)

    val protocol = MutableLiveData<String>()
    val apiUrl = MutableLiveData<String>()
    val apiHost = MutableLiveData<String>()
    val apiPath = MutableLiveData<String?>()
    val apiQueries = MutableLiveData<String?>()

    val mappingNickName = MutableLiveData("")

    val httpCode = MutableLiveData("")
    val mapToSuccessResponse = MutableLiveData(true)
    val successResponse = MutableLiveData("")
    val errorResponse = MutableLiveData("")

    val selectedResponseMapping = mapToSuccessResponse.map {
        if(it) R.id.rd_success_type else R.id.rd_error_type
    }
    val successResponseVisible = MutableLiveData(false)
    val successResponseAction = successResponseVisible.map {
        if (it) R.string.kashproxy_click_to_collapse else R.string.kashproxy_click_to_expand
    }

    val errorResponseVisible = MutableLiveData(false)
    val errorResponseAction = errorResponseVisible.map {
        if (it) R.string.kashproxy_click_to_collapse else R.string.kashproxy_click_to_expand
    }

    val editMapping = SingleLiveData<MappingItem>()

    private var isSaving = false

    init {
        viewModelScope.launch {
            url?.let { mappingUrl ->
                val existingMapping = KashProxyLib.getMappingRepository().getMappingByUrl(mappingUrl)
                when {
                    existingMapping == null && mapUrlModel == null -> handleInvalidMapping(
                        mappingUrl
                    )

                    mapUrlModel == null && existingMapping != null -> populateFromMapping(
                        existingMapping
                    )

                    existingMapping == null && mapUrlModel != null -> createNewMappingResponse(
                        mapUrlModel
                    )

                    existingMapping != null && mapUrlModel != null -> handleExistingMapping(
                        existingMapping, mapUrlModel
                    )
                }
            }
            formatUrl()
        }
    }

    private fun handleExistingMapping(
        mapping: ResponseMappingModel,
        mapUrlModel: MapUrlModel
    ) {
        showAlertDialog(title = R.string.kashproxy_existing_mapping_warning_title,
            message = R.string.kashproxy_existing_mapping_warning_msg,
            positiveButtonTxt = R.string.kashproxy_use_existing,
            negativeButtonTxt = R.string.kashproxy_use_new,
            positiveClickListen = {
                populateFromMapping(mapping)
            },
            negativeClickListen = {
                createNewMappingResponse(mapUrlModel)
            })
    }

    private fun populateFromMapping(mapping: ResponseMappingModel) {
        apiUrl.value = mapping.url
        protocol.value = mapping.protocol
        apiHost.value = mapping.apiHost
        apiPath.value = mapping.path
        apiQueries.value = mapping.queries
        mapToSuccessResponse.value = mapping.mapToSuccess
        mappingNickName.value = mapping.mappingNickName
        httpCode.value = mapping.httpCode.toString()
        successResponse.value = mapping.successResponse
        errorResponse.value = mapping.errorResponse
        mappingEnabled.value = mapping.mappingEnabled
    }


    private fun createNewMappingResponse(mapUrlModel: MapUrlModel) {
        apiUrl.value = mapUrlModel.url
        successResponse.value = mapUrlModel.response
        errorResponse.value =
            getApplication<Application>().getString(R.string.kashproxy_proxy_error_response_template)
                .formatToJson()
    }

    private fun handleInvalidMapping(url: String) {
        showAlertDialog(
            title = R.string.kashproxy_invalid_mapping,
            message = R.string.kashproxy_invalid_mapping_msg,
            positiveButtonTxt = R.string.kashproxy_create_new,
            negativeButtonTxt = R.string.kashproxy_cancel,
            positiveClickListen = {
                apiUrl.value = url
                errorResponse.value =
                    getApplication<Application>().getString(R.string.kashproxy_proxy_error_response_template)
                        .formatToJson()
            },
            negativeClickListen = { navigateBack() }
        )
    }

    fun onApiUrlChanged(url: CharSequence?) {
        if (!url.isNullOrBlank() && this.apiUrl.value != url) {
            this.apiUrl.value = url.toString()
        }
    }

    fun onHostChanged(host: CharSequence?) {
        if (!host.isNullOrBlank() && this.apiHost.value != host) {
            this.apiHost.value = host.toString()
        }
    }

    fun onPathChanged(path: CharSequence?) {
        if (!path.isNullOrBlank() && this.apiPath.value != path) {
            this.apiPath.value = path.toString()
        }
    }

    fun onQueryChanged(query: CharSequence?) {
        if (!query.isNullOrBlank() && this.apiQueries.value != query) {
            this.apiQueries.value = query.toString()
        }
    }

    fun onResponseTypeChanged(responseTypeId: Int?) {
        when (responseTypeId) {
            R.id.rd_success_type -> {
                mapToSuccessResponse.value = true
            }

            else -> {
                mapToSuccessResponse.value = false
            }
        }
    }

    fun toggleSuccessResponseExpansion() {
        successResponseVisible.value = !(successResponseVisible.value ?: false)
    }

    fun toggleErrorResponseExpansion() {
        errorResponseVisible.value = !(errorResponseVisible.value ?: false)
    }

    fun editMappingResponse() {
        editMapping.value = MappingItem(
            httpCode = httpCode.value,
            successResponse = successResponse.value,
            errorResponse = errorResponse.value,
            mapToSuccess = mapToSuccessResponse.value == true
        )
    }

    fun deleteMapping() {
        apiUrl.value?.let {
            showAlertDialog(title = R.string.kashproxy_confirm,
                message = R.string.kashproxy_confirm_delete_mapping,
                positiveButtonTxt = R.string.kashproxy_delete,
                negativeButtonTxt = R.string.kashproxy_cancel,
                positiveClickListen = {
                    viewModelScope.launch {
                        KashProxyLib.getMappingRepository().deleteProxyMapping(it)
                        navigateBack()
                    }
                })
        }
    }

    fun formatUrl() {

        try {
            apiUrl.value?.let {
                URI.create(it).run {
                    apiUrlError.value = null
                    if (!scheme.isNullOrBlank()) {
                        protocol.value = scheme
                    }

                    apiHost.value = authority ?: host
                    apiPath.value = path
                    apiQueries.value = query
                    apiUrl.value = this.toURL().toString()
                }
            }
            apiUrlError.value = null
        } catch (e: Exception) {
            apiUrlError.value = R.string.kashproxy_invalid_url
            showToast(R.string.kashproxy_invalid_url)
            e.printStackTrace()
        }
    }

    fun saveMapping() {
        formatUrl()
        if (apiUrl.value == null) {
            apiUrlError.value = R.string.kashproxy_please_select_a_value
            return
        } else {
            apiUrlError.value = null
        }

        apiUrl.value?.let {
            if (isSaving) return else isSaving = true
            viewModelScope.launch {
                ResponseMappingModel(url = it,
                    protocol = protocol.value ?: "https",
                    apiHost = apiHost.value ?: "",
                    path = apiPath.value,
                    queries = apiQueries.value,
                    mapToSuccess = mapToSuccessResponse.value == true,
                    mappingNickName = mappingNickName.value,
                    httpCode = httpCode.value?.takeIf { it.isNotBlank() && it.isDigitsOnly() }
                        ?.toInt() ?: 400,
                    successResponse = successResponse.value,
                    errorResponse = errorResponse.value,
                    mappingEnabled = mappingEnabled.value == true).let {
                    KashProxyLib.getMappingRepository().insertProxyMapping(it)
                    isSaving = false
                    navigate(MappingDetailsFragmentDirections.showMappingsList())
                }
            }
        }
    }
}