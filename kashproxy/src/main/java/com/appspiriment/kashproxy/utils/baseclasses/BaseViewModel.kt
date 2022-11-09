package com.appspiriment.kashproxy.utils.baseclasses

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.appspiriment.kashproxy.utils.alerts.AlertDialogModel
import com.appspiriment.kashproxy.utils.events.Event
import com.appspiriment.kashproxy.utils.events.MessageEvent
import com.appspiriment.kashproxy.utils.events.SingleLiveData
import com.appspiriment.kashproxy.utils.navigation.NavigationCommand
import com.appspiriment.kashproxy.utils.navigation.StartActivityModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*********************************************************
 * Class   :  BaseViewModel
 * Author  :  Arun Nair
 * Created :  13/2/21
 *******************************************************
 * Purpose :  Handles base variables in common-ui
 *******************************************************
 * Rework Details:
 * 1) {Author} :  {Date} : {Details}
 *********************************************************/

internal abstract class BaseViewModel : ViewModel() {

    val navigation = SingleLiveData<NavigationCommand>()
    val messageEvent = MutableLiveData<MessageEvent<*>>()
    val event = MutableLiveData<Event<*>>()
    val startActivity = SingleLiveData<StartActivityModel>()

    /***************************************
     * On Backpress
     ***************************************/
    fun dismissKeyBoard(dismiss: Boolean = true) {
        event.postValue(Event(dismiss, Event.EventType.DISMISS_KEYBOARD))
    }

    /***************************************
     *  Set SnackBar
     ***************************************/
    fun showSnackBar(
        message: Any? = null,
        snackBarDuration: Int = BaseTransientBottomBar.LENGTH_SHORT
    ) {
        messageEvent.postValue(MessageEvent.getSnackBarEvent(message, snackBarDuration))
    }

    /***************************************
     *  Set SnackBar
     ***************************************/
    fun showToast(
        message: Any? = null,
        toastDuration: Int = Toast.LENGTH_SHORT
    ) {
        messageEvent.postValue(MessageEvent.getToastEvent(message, toastDuration))
    }

    /***************************************
     *  Set SnackBar
     ***************************************/
    fun showAlertDialog(
        title: Any,
        message: Any,
        positiveButtonTxt: Any? = null,
        negativeButtonTxt: Any? = null,
        neutralButtonTxt: Any? = null,
        finishActivityOnOk: Boolean = false,
        finishActivityAffinity: Boolean = false,
        finishActivityOnCancel: Boolean = false,
        isCancellable: Boolean = false,
        positiveClickListen: () -> Unit = {},
        negativeClickListen: () -> Unit = {},
        neutralClickListen: () -> Unit = {},
        onCancelListen: () -> Unit = {},
        iconRes: Int? = null,
        isHtmlMessage: Boolean = false
    ) {
        AlertDialogModel(
            title = title,
            message = message,
            positiveButtonTxt = positiveButtonTxt,
            negativeButtonTxt = negativeButtonTxt,
            neutralButtonTxt = neutralButtonTxt,
            finishActivityOnOk = finishActivityOnOk,
            finishActivityAffinity = finishActivityAffinity,
            finishActivityOnCancel = finishActivityOnCancel,
            isCancellable = isCancellable,
            positiveClickListen = positiveClickListen,
            negativeClickListen = negativeClickListen,
            neutralClickListen = neutralClickListen,
            onCancelListen = onCancelListen,
            iconRes = iconRes,
            isHtmlMessage = isHtmlMessage
        ).let {
            messageEvent.postValue(MessageEvent.getAlertDialog(it))
        }
    }

    /***************************************
     * Convenient method to handle navigation from a [ViewModel]
     ***************************************/
    fun navigate(uri: Uri? = null) {
        navigation.postValue(uri?.let { NavigationCommand.DeepLink(it) })
    }

    /***************************************
     * Convenient method to handle navigation from a [ViewModel]
     ***************************************/
    fun navigate(directions: NavDirections?) {
        navigation.postValue(
            directions?.let { NavigationCommand.To(it) }
        )
    }

    /***************************************
     * Convenient method to handle back navigation from a [ViewModel]
     ***************************************/
    fun navigateBack() = viewModelScope.launch(Dispatchers.Main) {
        navigation.postValue(NavigationCommand.Back)
    }

    fun navigatePopBack() {
        navigation.postValue(NavigationCommand.POP)
    }

    /***************************************
     * Convenient method to handle back navigation from a [ViewModel]
     ***************************************/
    fun finishActivity() {
        navigation.postValue(NavigationCommand.FinishActivity)
    }

    /***************************************
     * Convenient method to handle navigation from a [ViewModel]
     ***************************************/
    fun startActivity(
        activityClass: Class<out Activity>? = null, action: String? = null, extras: Bundle? = Bundle(),
        isClearStack: Boolean = false
    ) {
        startActivity.postValue(
            StartActivityModel(
                activityClass, action, extras?:Bundle(), isClearStack
            )
        )
    }

    /***************************************
     * Convenient method to handle navigation from a [ViewModel]
     ***************************************/
    fun startActivity(startActivityModel: StartActivityModel) {
        startActivity.postValue(startActivityModel)
    }
}