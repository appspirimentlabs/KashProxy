package com.appspiriment.kashproxy.demo

import android.app.Application
import com.appspiriment.kashproxy.demo.di.KashProxyDemoApp

class DemoApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        KashProxyDemoApp.initialize(applicationContext)

    }
}