package com.appspiriment.kashproxy.utils.alerts

/*********************************************************
 * Class   :  AlertDialogEvent
 * Author  :  Arun Nair
 * Created :  19/05/22
 *******************************************************
 * Purpose :  data class for Alert Message dialog
 *******************************************************
 * Rework Details:
 * 1) {Author} :  {Date} : {Details}
 *********************************************************/
data class AlertDialogModel (
    val title: Any,
    val message: Any,
    val view: Any? = null,
    val positiveButtonTxt: Any? = null,
    val negativeButtonTxt: Any? = null,
    val neutralButtonTxt: Any? = null,
    val finishActivityOnOk: Boolean = false,
    val finishActivityAffinity: Boolean = false,
    val finishActivityOnCancel: Boolean = false,
    val isCancellable: Boolean = false,
    val positiveClickListen: () -> Unit = {},
    val negativeClickListen: () -> Unit = {},
    val neutralClickListen: () -> Unit = {},
    val genericClickListen: (Int) -> Unit = {},
    val onCancelListen: () -> Unit = {},
    val iconRes: Int? = null,
    val isHtmlMessage: Boolean = false
)