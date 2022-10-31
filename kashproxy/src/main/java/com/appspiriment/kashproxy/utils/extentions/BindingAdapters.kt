package com.appspiriment.kashproxy.utils.extentions

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.ArrayRes
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener


/*********************************************************
 * Class   :  BindingAdapters
 * Author  :  Arun Nair
 * Created :  15/09/2022
 *******************************************************
 * Purpose :
 *******************************************************
 * Rework Details:
 * 1) {Author} :  {Date} : {Details}
 *********************************************************/

/***************************************
 * Setting Observers
 ***************************************/
@BindingAdapter("isVisible")
fun View.showHide(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

/***************************************
 * Setting Observers
 ***************************************/
@BindingAdapter("errorRes")
fun TextView.setTextError(resId: Int?) {
    return try {
        error = resId?.let { context.resources.getString(it) }
    } catch (e: Exception) {
        error =  null
    }
}


/***************************************
 * Setting Observers
 ***************************************/
@BindingAdapter("entries")
fun Spinner.setEntries(@ArrayRes entries: Int) {
    adapter = ArrayAdapter.createFromResource(
        context,
        entries,
        android.R.layout.simple_spinner_item
    ).apply {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }
}


/***************************************
 * Setting Observers
 ***************************************/
@BindingAdapter("onDone")
fun EditText.onDone(callback: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            callback.invoke()
            return@setOnEditorActionListener true
        }
        false
    }
}

/***************************************
 * Setting Observers
 ***************************************/
@BindingAdapter(value = ["selectedValue", "selectedValueAttrChanged"], requireAll = false)
fun Spinner.bindSpinnerData(
    newSelectedValue: String?,
    newTextAttrChanged: InverseBindingListener
) {
    onItemSelectedListener = object : OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            newTextAttrChanged.onChange()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
    setSelection((adapter as ArrayAdapter<String>).getPosition(newSelectedValue))
}

@InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
fun Spinner.captureSelectedValue(): String? {
    return selectedItem.toString()
}

