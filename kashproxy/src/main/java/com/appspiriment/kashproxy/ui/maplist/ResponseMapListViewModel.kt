package com.appspiriment.kashproxy.ui.maplist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.appspiriment.kashproxy.data.preference.isKashProxyMappingEnabled
import com.appspiriment.kashproxy.di.KashProxyLib
import com.appspiriment.kashproxy.ui.model.ResponseMappingModel
import com.appspiriment.kashproxy.utils.events.SingleLiveData
import kotlinx.coroutines.launch

internal class ResponseMapListViewModel(application: Application): AndroidViewModel(application) {

    val openMappingDetails = SingleLiveData<Int?>()
    val mappingList = MutableLiveData<List<ResponseMappingModel>>(emptyList())
    val mappingEnabled = MutableLiveData<Boolean>()

    init{
        mappingEnabled.value = application.isKashProxyMappingEnabled()
    }
    
    fun getMappingList(){
        viewModelScope.launch {
            mappingList.postValue(KashProxyLib.getMappingRepository().getAllMapping())
        }
    }

    fun addMapping(){
        openMappingDetails.value = null
    }


    fun openMappingDetails(mappingId:Int){
        openMappingDetails.value = mappingId
    }

    fun changeResponseType(model: ResponseMappingModel, toSuccess:Boolean){
        viewModelScope.launch {
            KashProxyLib.getMappingRepository().insertProxyMapping(model.apply { mapToSuccess = toSuccess })
            getMappingList()
        }
    }
    fun enableMapping(model: ResponseMappingModel, enable:Boolean){
        viewModelScope.launch {
            KashProxyLib.getMappingRepository().insertProxyMapping(model.apply { mappingEnabled = enable })
            getMappingList()
        }
    }

}
