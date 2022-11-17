package com.appspiriment.kashproxy.ui.main

import android.content.Context
import android.content.Intent
import com.appspiriment.kashproxy.R
import com.appspiriment.kashproxy.di.KashProxyLib
import com.appspiriment.kashproxy.ui.model.MapUrlModel
import com.appspiriment.kashproxy.utils.baseclasses.NavigationActivity

internal class KashProxyActivity : NavigationActivity() {
    override var navGraphId: Int = R.navigation.kashproxy_navigation_main

    override fun setNavGraph() {
        navGraphId.takeIf { it != -1 }?.let { navId ->
            navController.run {
                navInflater.inflate(navId).apply {
                    setStartDestination(
                        if (intent.hasExtra(EXTRA_NAME_MAP_MODEL)) R.id.kashResponseMappingFragment else R.id.kashMapListFragment // kashMappingResponseEditFragment
                    )
                }.let {
                    setGraph(it, intent.extras)
                }
            }
        }
    }

    companion object {
        const val EXTRA_NAME_MAP_URL = "mappingUrl"
        const val EXTRA_NAME_MAP_MODEL = "map_model"
        fun show(context: Context, mapModel: MapUrlModel? = null) {
            getLauncherIntent(context, mapModel).let {
                context.startActivity(it)
            }
        }

        fun getLauncherIntent(context: Context, mapModel: MapUrlModel? = null): Intent {
            KashProxyLib.initialize(context)
            return Intent(context, KashProxyActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                mapModel?.let{
                    putExtra(EXTRA_NAME_MAP_URL, it.url)
                    putExtra(EXTRA_NAME_MAP_MODEL, it)
                }
            }
        }
    }
}