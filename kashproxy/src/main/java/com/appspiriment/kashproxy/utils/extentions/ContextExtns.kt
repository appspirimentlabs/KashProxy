package com.appspiriment.kashproxy.utils.extentions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.appspiriment.kashproxy.utils.events.SingleLiveData
import com.appspiriment.kashproxy.utils.navigation.StartActivityModel


/*********************************************************
 * Class   :  AlertExtns
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
internal fun Activity.handleStartActivity(activityModel: StartActivityModel) {
    startActivity(Intent().apply {
        activityModel.activityClass?.let { setClass(this@handleStartActivity, it) }
        action = activityModel.action
        putExtras(activityModel.extras)
        if (activityModel.clearStack)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    })
}


/***************************************
 * Setting Observers
 ***************************************/
internal fun <T> Fragment.observeData(liveData: LiveData<T>, observer: (value: T) -> Unit) {
    liveData.observe(viewLifecycleOwner) { value ->
        observer(value)
    }
}

/***************************************
 * Setting Observers
 ***************************************/
internal fun <T> Fragment.observeData(liveData: SingleLiveData<T>, observer: (value: T) -> Unit) {
    liveData.observe(viewLifecycleOwner) { value ->
        observer(value)
    }
}

/***************************************
 * Setting Observers
 ***************************************/
internal fun <T> AppCompatActivity.observeData(liveData: LiveData<T>, observer: (value: T) -> Unit) {
    liveData.observe(this) { value ->
        observer(value)
    }
}

/***************************************
 * Setting Observers
 ***************************************/
internal fun Fragment.openFragmentForResult(
    direction: NavDirections,
    requestKey: String,
    listener: (bundle: Bundle) -> Unit
) {
    setFragmentResultListener(requestKey) { key, bundle ->
        if (key == requestKey) {
            listener.invoke(bundle)
        }
    }
    findNavController().navigate(direction)
}
