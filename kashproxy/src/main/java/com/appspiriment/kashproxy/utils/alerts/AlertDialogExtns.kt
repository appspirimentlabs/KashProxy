package com.appspiriment.kashproxy.utils.alerts

import android.app.Activity
import android.content.DialogInterface
import android.text.Html
import android.view.View

/*********************************************************
 * Class   :  MessageUtils
 * Author  :  Arun Nair
 * Created :  19/05/22
 *******************************************************
 * Purpose :  Extn Functions for Message
 *******************************************************
 * Rework Details:
 * 1) {Author} :  {Date} : {Details}
 *********************************************************/

fun Activity.showAlertDialog(alertDialog: AlertDialogModel) {
    alertDialog.let {
        getAlertDialogBuilder(
            activity = this,
            title = it.title,
            message = it.message,
            view = it.view,
            positiveButton = it.positiveButtonTxt,
            negativeButton = it.negativeButtonTxt,
            neutralButton = it.neutralButtonTxt,
            finishActivityOnOk = it.finishActivityOnOk,
            finishActivityAffinity = it.finishActivityAffinity,
            finishActivityOnCancel = it.finishActivityOnCancel,
            isCancellable = it.isCancellable,
            positiveClickListen = it.positiveClickListen,
            negativeClickListen = it.negativeClickListen,
            neutralClickListen = it.neutralClickListen,
            genericClickListen = it.genericClickListen,
            onCancelListen = it.onCancelListen,
            iconRes = it.iconRes,
            isHtmlMessage = it.isHtmlMessage
        ).show()
    }
}


/**
 * **************************************
 * Method actionTo show an Alert Dialog with onClick Listeners
 * ****************************************
 */
fun Activity.showAlertDialog(
    title: Any,
    message: Any,
    view: Any? = null,
    positiveButton: Any,
    negativeButton: Any? = null,
    neutralButton: Any? = null,
    finishActivityOnOk: Boolean = false,
    finishActivityAffinity: Boolean = false,
    finishActivityOnCancel: Boolean = false,
    isCancellable: Boolean = false,
    positiveClickListen: () -> Unit = {},
    negativeClickListen: () -> Unit = {},
    neutralClickListen: () -> Unit = {},
    genericClickListen: (Int) -> Unit = {},
    onCancelListen: () -> Unit = {},
    iconRes: Int? = null,
    isHtmlMessage: Boolean = false
) {
    getAlertDialogBuilder(
        activity = this,
        title = title,
        message = message,
        view = view,
        positiveButton = positiveButton,
        negativeButton = negativeButton,
        neutralButton = neutralButton,
        finishActivityOnOk = finishActivityOnOk,
        finishActivityAffinity = finishActivityAffinity,
        finishActivityOnCancel = finishActivityOnCancel,
        isCancellable = isCancellable,
        positiveClickListen = positiveClickListen,
        negativeClickListen = negativeClickListen,
        neutralClickListen = neutralClickListen,
        genericClickListen = genericClickListen,
        onCancelListen = onCancelListen,
        iconRes = iconRes,
        isHtmlMessage = isHtmlMessage
    ).show()
}

/**
 * **************************************
 * Method actionTo show an Alert Dialog with onClick Listeners
 * ****************************************
 */
private fun getAlertDialogBuilder(
    activity: Activity,
    title: Any,
    message: Any,
    view: Any? = null,
    positiveButton: Any?,
    negativeButton: Any? = null,
    neutralButton: Any? = null,
    finishActivityOnOk: Boolean = false,
    finishActivityAffinity: Boolean = false,
    finishActivityOnCancel: Boolean = false,
    isCancellable: Boolean = false,
    positiveClickListen: () -> Unit = {},
    negativeClickListen: () -> Unit = {},
    neutralClickListen: () -> Unit = {},
    genericClickListen: (Int) -> Unit = {},
    onCancelListen: () -> Unit = {},
    iconRes: Int? = null,
    isHtmlMessage: Boolean = false
): androidx.appcompat.app.AlertDialog.Builder {

    val dialogView = when (view) {
        is View -> view
        is Int -> activity.layoutInflater.inflate(view, null)
        else -> null
    }

    val titleText = when (title) {
        is String -> title
        is Int -> activity.getString(title)
        else -> throw  Exception("Title should either be String or ResId!")
    }

    val messageText = when (message) {
        is String -> message
        is Int -> activity.getString(message)
        else -> throw  Exception("Messageshould either be String or ResId!")
    }

    val onClickListener = DialogInterface.OnClickListener { dialog, which ->
        dialog.dismiss()
        genericClickListen(which)
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> {
                positiveClickListen()
                if (finishActivityOnOk) {
                    if (finishActivityAffinity) activity.finishAffinity() else activity.finish()
                }
            }
            DialogInterface.BUTTON_NEGATIVE -> {
                negativeClickListen()
                if (finishActivityOnCancel)
                    if (finishActivityAffinity) activity.finishAffinity() else activity.finish()
            }
            DialogInterface.BUTTON_NEUTRAL -> neutralClickListen()
        }
    }

    val onCancelListener = DialogInterface.OnCancelListener { dialog ->
        dialog.dismiss()
        onCancelListen()
        if (finishActivityOnCancel)
            if (finishActivityAffinity) activity.finishAffinity() else activity.finish()
    }

    val builder = androidx.appcompat.app.AlertDialog.Builder(activity).apply {
        setTitle(titleText)
        messageText.let {
            setMessage(
                if (isHtmlMessage) Html.fromHtml(it) else it
            )
        }
        setCancelable(isCancellable)
        iconRes?.let { setIcon(it) }
        dialogView?.let { setView(it) }
        setOnCancelListener(onCancelListener)
    }

    return getBuilderWithDialogButtons(
        builder,
        activity,
        positiveButton,
        negativeButton,
        neutralButton,
        onClickListener
    )
}

/**
 * **************************************
 * Method actionTo show an Alert Dialog with onClick Listeners
 * ****************************************
 */
private fun getBuilderWithDialogButtons(
    builder: androidx.appcompat.app.AlertDialog.Builder,
    activity: Activity,
    positiveButtonTxt: Any? = null,
    negativeButtonTxt: Any? = null,
    neutralButtonTxt: Any? = null,
    clickListener: DialogInterface.OnClickListener
): androidx.appcompat.app.AlertDialog.Builder {
    return builder.apply {

        when (positiveButtonTxt) {
            is String -> positiveButtonTxt
            is Int -> activity.getString(positiveButtonTxt)
            else -> "OK"
        }.let { setPositiveButton(it, clickListener) }

        when (negativeButtonTxt) {
            is String -> negativeButtonTxt
            is Int -> activity.getString(negativeButtonTxt)
            else -> null
        }?.let { setNegativeButton(it, clickListener) }

        when (neutralButtonTxt) {
            is String -> neutralButtonTxt
            is Int -> activity.getString(neutralButtonTxt)
            else -> null
        }?.let { setNeutralButton(it, clickListener) }
    }
}
