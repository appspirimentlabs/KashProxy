package com.appspiriment.kashproxy.utils.navigation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections

/**
 * A simple sealed class to handle more properly
 * navigation from a [ViewModel]
 */
sealed class NavigationCommand {
    data class To(val directions: NavDirections) : NavigationCommand()
    data class DeepLink(val uri : Uri) : NavigationCommand()
    object Back : NavigationCommand()
    object POP : NavigationCommand()
    object FinishActivity : NavigationCommand()
}