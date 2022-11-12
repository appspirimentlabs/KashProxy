package com.appspiriment.kashproxy.ui.rewritemappingsheet

import androidx.lifecycle.MutableLiveData
import com.appspiriment.kashproxy.utils.baseclasses.BaseViewModel
import com.appspiriment.kashproxy.R
import com.appspiriment.kashproxy.utils.events.SingleLiveData

internal class ResponseStatusRewriteViewModel: BaseViewModel() {

    val fromCode = MutableLiveData<String>()
    val fromCodeError = MutableLiveData<Int?>()
    val toCode = MutableLiveData<String>()
    val toCodeError = MutableLiveData<Int?>()
    val saveMapping = SingleLiveData<Unit>()

    fun saveMapping(){
        dismissKeyBoard()

        fromCodeError.value = if(fromCode.value == null) R.string.kashproxy_please_select_a_value else null
        toCodeError.value = if(toCode.value == null) R.string.kashproxy_please_select_a_value else null

        if(fromCodeError.value == null && toCodeError.value == null){
            saveMapping.value = Unit
        }
    }

    fun fromCodeChanged(code: CharSequence?){
        if(fromCode.value != code) fromCode.value = code?.toString()
    }

    fun toCodeChanged(code: CharSequence?){
        if(toCode.value != code) toCode.value = code?.toString()
    }
}