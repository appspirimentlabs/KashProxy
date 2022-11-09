package com.appspiriment.kashproxy.demo.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.appspiriment.kashproxy.demo.di.KashProxyDemoApp
import com.appspiriment.kashproxy.demo.network.ApiResult
import com.appspiriment.kashproxy.api.KashProxy
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DemoViewModel(application: Application) : AndroidViewModel(application) {
    val mappingEnabled = MutableLiveData<Boolean>()
    val host = MutableLiveData("")
    val path = MutableLiveData("")
    val apiResult = MutableLiveData<ApiResult>(ApiResult())
    val isLoading = MutableLiveData(false)
    val copyUrl = MutableLiveData("")

    init{
        mappingEnabled.value = KashProxy.isKashProxyMappingEnabled(application)
    }

    fun checkMappingEnabled() {
        mappingEnabled.value = KashProxy.isKashProxyMappingEnabled(getApplication<Application>().applicationContext)
    }

    fun copyUrl(){
        copyUrl.value = KashProxyDemoApp.getNetworkRepo().getApiUrl(host.value, path.value)
    }

    fun showMappings() {
        KashProxy.showMappingActivity(getApplication<Application>().applicationContext)
    }

    fun showLogs() {
        KashProxy.showChuckerActivity(getApplication<Application>().applicationContext)
    }

    fun fetchApiResult() {
        viewModelScope.launch {
            isLoading.value = true
            apiResult.value = KashProxyDemoApp.getNetworkRepo().getApiResult(host.value, path.value)
            delay(300)
            isLoading.value = false
        }
    }
}