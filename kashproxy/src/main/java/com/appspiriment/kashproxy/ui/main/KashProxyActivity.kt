package com.appspiriment.kashproxy.ui.main

import android.content.Context
import android.content.Intent
import com.appspiriment.kashproxy.R
import com.appspiriment.kashproxy.di.KashProxyApp
import com.appspiriment.kashproxy.utils.baseclasses.NavigationActivity

internal class KashProxyActivity : NavigationActivity() {
    override var navGraphId: Int = R.navigation.navigation_proxy


    companion object {
        fun show(context: Context) {
            KashProxyApp.initialize(context)
            Intent(context, KashProxyActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }.also {
                context.startActivity(it)
            }
        }
    }
}