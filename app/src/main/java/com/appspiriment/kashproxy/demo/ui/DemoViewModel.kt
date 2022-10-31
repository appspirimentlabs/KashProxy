package com.appspiriment.kashproxy.demo.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.appspiriment.kashproxy.data.preference.isKashProxyMappingEnabled
import com.appspiriment.kashproxy.demo.di.KashProxyDemoApp
import com.appspiriment.kashproxy.demo.network.ApiResult
import com.appspiriment.kashproxy.di.KashProxyApp
import com.appspiriment.kashproxy.utils.baseclasses.BaseAndroidViewModel
import com.appspiriment.kashproxy.utils.events.SingleLiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DemoViewModel(application: Application) : BaseAndroidViewModel(application) {
    val mappingEnabled = MutableLiveData(false)
    val host = MutableLiveData("")
    val path = MutableLiveData("")
    val apiResult = MutableLiveData<ApiResult>(ApiResult())
    val isLoading = MutableLiveData(false)
    val copyUrl = MutableLiveData("")

    val showMapping = SingleLiveData<Unit>()

    init {
        mappingEnabled.value = application.applicationContext?.isKashProxyMappingEnabled()
    }

    fun copyUrl(){
        copyUrl.value = KashProxyDemoApp.getNetworkRepo().getApiUrl(host.value, path.value)
    }

    fun showMappings() {
        KashProxyApp.showMappingActivity(getApplication<Application>().applicationContext)
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