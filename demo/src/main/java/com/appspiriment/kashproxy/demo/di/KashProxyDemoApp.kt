package com.appspiriment.kashproxy.demo.di

import android.content.Context
import com.appspiriment.kashproxy.demo.network.NetworkRepository
import com.appspiriment.kashproxy.demo.network.NetworkModule

object KashProxyDemoApp {

    private var network: NetworkModule? = null
    private var networkRepo: NetworkRepository? = null
    val baseUrl = "https://digithreads-workshop-default-rtdb.europe-west1.firebasedatabase.app/"

    fun initialize(context: Context) {
        network = NetworkModule(context).also {
            networkRepo = NetworkRepository(it)
        }
    }

    fun getNetwork(): NetworkModule {
        return network ?: run {
            throw RuntimeException("Demo App not properly initialized. Please call 'KashProxyDemoApp.initialize(context)' in Application Class")
        }
    }

    fun getNetworkRepo(): NetworkRepository {
        return networkRepo ?: run {
            throw RuntimeException("Demo App not properly initialized. Please call 'KashProxyDemoApp.initialize(context)' in Application Class")
        }
    }

}