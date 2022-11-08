package com.appspiriment.kashproxy.ui.mappingdetail

import android.app.Application
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.appspiriment.kashproxy.R
import com.appspiriment.kashproxy.di.KashProxy
import com.appspiriment.kashproxy.ui.model.MapUrlModel
import com.appspiriment.kashproxy.ui.model.MappingItem
import com.appspiriment.kashproxy.ui.model.ResponseMappingModel
import com.appspiriment.kashproxy.utils.baseclasses.BaseAndroidViewModel
import com.appspiriment.kashproxy.utils.events.SingleLiveData
import com.appspiriment.kashproxy.utils.extentions.formatToJson
import kotlinx.coroutines.launch
import java.net.URI

class MappingDetailsViewModel(
    application: Application,
    private val url: String?,
    private val mapUrlModel: MapUrlModel?
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
    val mapToSuccessResponse = MutableLiveData(false)
    val successResponse = MutableLiveData("")
    val errorResponse = MutableLiveData("")

    val selectedResponseMapping = MutableLiveData(R.id.rd_error_type)
    val successResponseVisible = MutableLiveData(false)
    val successResponseAction = successResponseVisible.map {
        if (it) R.string.kash_click_to_collapse else R.string.kash_click_to_expand
    }

    val errorResponseVisible = MutableLiveData(false)
    val errorResponseAction = errorResponseVisible.map {
        if (it) R.string.kash_click_to_collapse else R.string.kash_click_to_expand
    }

    val deleteMapping = SingleLiveData<String?>()
    val editMapping = SingleLiveData<MappingItem>()

    private var isSaving = false

    init {
        if (mapUrlModel != null) {
            apiUrl.value = mapUrlModel.url
            successResponse.value = mapUrlModel.response
            errorResponse.value =
                getApplication<Application>().getString(R.string.kash_error_dummy_response)
                    .formatToJson()
            formatUrl()
        } else {
            viewModelScope.launch {
                KashProxy.getMappingRepository().getMappingByUrl(url)?.let {
                    apiUrl.value = it.url
                    protocol.value = it.protocol
                    apiHost.value = it.apiHost
                    apiPath.value = it.path
                    apiQueries.value = it.queries
                    mapToSuccessResponse.value = it.mapToSuccess
                    mappingNickName.value = it.mappingNickName
                    httpCode.value = it.httpCode.toString()
                    successResponse.value = it.successResponse
                    errorResponse.value = it.errorResponse
                    mappingEnabled.value = it.mappingEnabled
                }
            }
        }

//        successResponse.value = getApplication<Application>().getString(R.string.dummy_response)

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
            errorResponse = errorResponse.value
        )
    }

    fun deleteMapping() {
        apiUrl.value?.let {
            showAlertDialog(
                title = R.string.kash_confirm,
                message = R.string.kash_confirm_delete_mapping,
                positiveButtonTxt = R.string.kash_delete,
                negativeButtonTxt = R.string.kash_cancel,
                positiveClickListen = {
                    viewModelScope.launch {
                        KashProxy.getMappingRepository().deleteProxyMapping(it)
                        navigateBack()
                    }
                }
            )
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
            apiUrlError.value = R.string.kash_invalid_url
            showToast(R.string.kash_invalid_url)
            e.printStackTrace()
        }
    }

    fun saveMapping() {
        formatUrl()
        if (apiUrl.value == null) {
            apiUrlError.value = R.string.kash_please_select_a_value
            return
        } else {
            apiUrlError.value = null
        }

        apiUrl.value?.let {
            if (isSaving) return else isSaving = true
            viewModelScope.launch {
                ResponseMappingModel(
                    url = it,
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
                    mappingEnabled = mappingEnabled.value == true
                ).let {
                    KashProxy.getMappingRepository().insertProxyMapping(it)
                    isSaving = false
                    navigate(MappingDetailsFragmentDirections.showMappingsList())
                }
            }
        }
    }
}