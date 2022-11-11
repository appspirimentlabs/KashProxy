package com.appspiriment.kashproxy.ui.responseeditor

import androidx.lifecycle.MutableLiveData
import com.appspiriment.kashproxy.utils.baseclasses.BaseViewModel
import com.appspiriment.kashproxy.R
import com.appspiriment.kashproxy.ui.model.MappingItem
import com.appspiriment.kashproxy.utils.events.SingleLiveData
import com.appspiriment.kashproxy.utils.extentions.formatToJson

internal class ResponseEditingViewModel(mappingItem: MappingItem?) : BaseViewModel() {


    val httpCode = MutableLiveData("400")
    val httpCodeError = MutableLiveData<Int?>()
    val isSuccessResponse = MutableLiveData(false)
    val selectedResponseType = MutableLiveData(R.id.rd_error_type)


    val responseSuccessBody = MutableLiveData("")
    val responseErrorBody = MutableLiveData("")

    val saveMappingItem = SingleLiveData<MappingItem>()


    fun httpCodeChanged(code: CharSequence?) {
        if (httpCode.value != code) httpCode.value = code?.toString()
    }


    fun onResponseTypeChanged(responseTypeId: Int?) {
        when(responseTypeId){
            R.id.rd_success_type ->{
                isSuccessResponse.value = true
            }
            else -> {
                isSuccessResponse.value = false
            }
        }
    }


    fun onSuccessResponseChanged(body: CharSequence?) {
        if (!body.isNullOrBlank() && this.responseSuccessBody.value != body) {
            this.responseSuccessBody.value = body.toString()
        }
    }

    fun onErrorResponseChanged(body: CharSequence?) {
        if (!body.isNullOrBlank() && this.responseErrorBody.value != body) {
            this.responseErrorBody.value = body.toString()
        }
    }

   init {
        mappingItem?.let {
            httpCode.value = it.httpCode.takeUnless { it.isNullOrBlank() }?: "400"
            selectedResponseType.value = if(it.mapToSuccess) R.id.rd_success_type else R.id.rd_error_type
            responseSuccessBody.value = it.successResponse
            responseErrorBody.value = it.errorResponse
        }
    }

    fun saveMappingItem() {
        if (httpCode.value.isNullOrBlank()) {
            httpCodeError.value = R.string.kash_please_select_a_value
            return
        } else httpCodeError.value = null

        saveMappingItem.value = MappingItem(
            httpCode = httpCode.value,
            successResponse = responseSuccessBody.value.formatToJson(),
            errorResponse = responseErrorBody.value.formatToJson()
        )
    }

    companion object {
        const val REQ_EDIT_MAPPING_ITEM = "REQ_EDIT_MAPPING_ITEM"
    }
}