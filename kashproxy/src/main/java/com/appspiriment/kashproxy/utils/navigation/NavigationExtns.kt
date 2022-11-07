package com.appspiriment.kashproxy.utils.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


/***************************************
 * Setting Observers
 ***************************************/
fun Fragment.handleNavigation(navigationCommand: NavigationCommand) {
    try {
        findNavController().run {
            when (navigationCommand) {
                is NavigationCommand.To -> navigate(navigationCommand.directions)
                is NavigationCommand.DeepLink -> navigate(navigationCommand.uri)
                NavigationCommand.Back,
                is NavigationCommand.POP -> {
                    if (!navigateUp()) {
                        requireActivity().finish()
                    } else { /*Nothing to do */ }
                }
                is NavigationCommand.FinishActivity -> activity?.finish()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        requireActivity().finish()
    }
}