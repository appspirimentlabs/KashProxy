package com.appspiriment.kashproxy.demo

import android.app.Application
import com.appspiriment.kashproxy.demo.di.KashProxyDemoApp
import com.appspiriment.kashproxy.di.KashProxy

class DemoApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        KashProxyDemoApp.initialize(applicationContext)
        KashProxy.initialize(applicationContext)

    }
}