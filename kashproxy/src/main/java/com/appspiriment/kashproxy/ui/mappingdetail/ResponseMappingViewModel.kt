package com.appspiriment.kashproxy.ui.mappingdetail

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.appspiriment.kashproxy.utils.baseclasses.BaseViewModel
import com.appspiriment.kashproxy.R
import com.appspiriment.kashproxy.di.KashProxyApp
import com.appspiriment.kashproxy.ui.model.ResponseMappingModel
import com.appspiriment.kashproxy.utils.events.SingleLiveData
import kotlinx.coroutines.launch
import java.net.URI

class ResponseMappingViewModel : BaseViewModel() {

    val apiUrlError = MutableLiveData<Int?>()

    var mappingId = MutableLiveData<String?>()
    val protocol = MutableLiveData<String>()
    val apiUrl = MutableLiveData<String>()
    val apiHost = MutableLiveData<String>()
    val apiPath = MutableLiveData<String?>()
    val apiQueries = MutableLiveData<String?>()
    val isStatusRewriting = MutableLiveData(false)
    val rewriteMap = MutableLiveData<HashMap<Int, Pair<Int, Boolean>>>(hashMapOf())
    val isResponseMapping = MutableLiveData(false)
    val responseBody = MutableLiveData("")

    val addRewriteMap = SingleLiveData<Unit>()
    val saveMapping = SingleLiveData<ResponseMappingModel>()
    val deleteMapping = SingleLiveData<String>()

    private var isSaving = false

    fun initialize(id: String?) {
        viewModelScope.launch {
            KashProxyApp.getMappingRepository().getMappingById(id)?.let {
                protocol.value = it.protocol
                apiHost.value = it.apiHost
                apiPath.value = it.path
                apiQueries.value = it.queries
                isStatusRewriting.value = it.isResponseStatusRewrite
                rewriteMap.value = it.responseStatusMap
                isResponseMapping.value = it.isResponseMapping
                responseBody.value = it.responseBody
                mappingId.value = it.mappingId
                apiUrl.value = it.url
            }
        }
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

    fun onResponseBodyChanged(body: CharSequence?) {
        if (!body.isNullOrBlank() && this.responseBody.value != body) {
            this.responseBody.value = body.toString()
        }
    }

    fun onAddRewriteClick() {
        addRewriteMap.value = Unit
    }

    fun addStatusRewriting(fromCode: String?, toCode: String?) {
        if (fromCode?.isDigitsOnly() == true && toCode?.isDigitsOnly() == true) {
            rewriteMap.value?.apply {
                put(
                    fromCode.toInt(),
                    Pair(toCode.toInt(), true)
                )
            }.also {
                rewriteMap.value = it
            }
        }
    }

    fun deleteMapping() {
        mappingId.value?.let {
            showAlertDialog(
                title = R.string.kash_confirm,
                message = R.string.kash_confirm_delete_mapping,
                positiveButtonTxt = R.string.kash_delete,
                negativeButtonTxt = R.string.kash_cancel,
                positiveClickListen = {
                    viewModelScope.launch {
                        KashProxyApp.getMappingRepository().deleteProxyMapping(it)
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
        apiUrlError.value = apiUrl.value?.let {
            if (isSaving) return else isSaving = true
            viewModelScope.launch {
                ResponseMappingModel(
                    mappingId = mappingId.value,
                    url = it,
                    protocol = protocol.value ?: "https",
                    apiHost = apiHost.value ?: "",
                    path = apiPath.value,
                    queries = apiQueries.value,
                    isResponseStatusRewrite = isStatusRewriting.value ?: false,
                    isResponseMapping = isResponseMapping.value ?: false,
                    responseStatusMap = rewriteMap.value ?: hashMapOf(),
                    responseBody = responseBody.value
                ).let {
                    KashProxyApp.getMappingRepository().insertProxyMapping(it)
                    isSaving = false
                    navigateBack()
                }
            }
            null
        } ?: R.string.kash_please_select_a_value
    }
}