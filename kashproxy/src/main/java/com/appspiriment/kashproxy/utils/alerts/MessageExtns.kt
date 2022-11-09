package com.appspiriment.kashproxy.utils.alerts

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.appspiriment.kashproxy.utils.events.Event
import com.appspiriment.kashproxy.utils.events.MessageEvent
import com.google.android.material.snackbar.Snackbar

/***************************************
 * Show Exit Confirmation
 ***************************************/
internal fun <T> Fragment.showSnackbar(
    message: T,
    duration: Int = Snackbar.LENGTH_SHORT
) {
    val msgTxt = when (message) {
        is String -> message
        is Int -> getString(message)
        else -> throw Exception("Should provide either message or message resId for Snackbar")
    }
    Snackbar.make(requireView(), msgTxt, duration).show()
}


/***************************************
 * Show Exit Confirmation
 ***************************************/
internal fun <M> Fragment.showToast(
    message: M,
    isLong: Boolean = false
) {
    requireContext().showToast(message = message, isLong = isLong)
}

/***************************************
 * Show Exit Confirmation
 ***************************************/
internal fun <M> Context.showToast(
    message: M,
    isLong: Boolean = false
) {

    Toast.makeText(
        this,
        when (message) {
            is String -> message
            is Int -> getString(message)
            else -> throw Exception("Should provide either message or message resId for Toast")
        },
        if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    ).show()
}


/***************************************
 * Show Exit Confirmation
 ***************************************/
internal fun <T> Fragment.showMessageEvent(event: MessageEvent<T>?) {
    when (event?.eventType) {
        Event.EventType.SHOW_SNACKBAR -> showSnackbar(
            event.message,
            event.duration
        )
        else -> activity?.showMessageEvent(event)
    }
}


/***************************************
 * Show Exit Confirmation
 ***************************************/
internal fun <T> Activity.showMessageEvent(event: MessageEvent<T>?) {
    when (event?.eventType) {
        Event.EventType.SHOW_TOAST -> showToast(
            event.message,
            event.duration == Toast.LENGTH_LONG
        )
        Event.EventType.SHOW_SNACKBAR -> showToast(
            event.message,
            event.duration == Toast.LENGTH_LONG
        )
        Event.EventType.SHOW_ALERT_DIALOG -> {
            if (event.message is AlertDialogModel) showAlertDialog(event.message)
        }
        else -> {}
    }
}
