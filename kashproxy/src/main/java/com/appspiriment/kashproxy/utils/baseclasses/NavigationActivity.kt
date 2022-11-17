package com.appspiriment.kashproxy.utils.baseclasses

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.appspiriment.kashproxy.R

internal open class NavigationActivity : AppCompatActivity() {
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment
    }

    protected val navController by lazy {
        navHostFragment.navController
    }

    open var navGraphId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kashproxy_activity_navigation)

        setNavGraph()
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return super.onSupportNavigateUp()
    }

    open fun setNavGraph(){
        navGraphId.takeIf { it != -1 }?.let { navController.setGraph(it) }
    }
}