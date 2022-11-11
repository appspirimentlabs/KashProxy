package com.appspiriment.kashproxy.demo.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.appspiriment.kashproxy.demo.di.KashProxyDemoApp
import com.appspiriment.kashproxy.demo.network.ApiResult
import com.appspiriment.kashproxy.api.KashProxy
import com.appspiriment.kashproxy.demo.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DemoViewModel(application: Application) : AndroidViewModel(application) {
    val mappingEnabled = MutableLiveData<Boolean>()
    val url = MutableLiveData("https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&hourly=temperature_2m,relativehumidity_2m,windspeed_10m")
    val apiUrlError = MutableLiveData<Int>()
    val apiResult = MutableLiveData<ApiResult>(ApiResult())
    val isLoading = MutableLiveData(false)
    val copyUrl = MutableLiveData("")

    init{
        mappingEnabled.value = KashProxy.isKashProxyMappingEnabled(application)
    }

    fun checkMappingEnabled() {
        mappingEnabled.value = KashProxy.isKashProxyMappingEnabled(getApplication<Application>().applicationContext)
    }

    fun showMappings() {
        KashProxy.showMappingActivity(getApplication<Application>().applicationContext)
    }

    fun onUrlChanged(text: CharSequence) {
       if(url.value != text.toString()){
           url.value = text.toString()
       }
    }

    fun showLogs() {
        KashProxy.showChuckerActivity(getApplication<Application>().applicationContext)
    }

    fun fetchApiResult() {
        if(url.value.isNullOrBlank()){
            apiUrlError.value = R.string.url_error_prompt
        } else {
            apiUrlError.value = null
            viewModelScope.launch {
                isLoading.value = true
                apiResult.value = KashProxyDemoApp.getNetworkRepo().getApiResult(url.value)
                delay(300)
                isLoading.value = false
            }
        }
    }
}