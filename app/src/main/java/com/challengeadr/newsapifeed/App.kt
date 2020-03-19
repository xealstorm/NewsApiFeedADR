package com.challengeadr.newsapifeed

import android.app.Application
import android.content.Context
import com.challengeadr.newsapifeed.di.app.AppComponent
import com.challengeadr.newsapifeed.di.app.DaggerAppComponent

class App : Application() {

    var appComponent: AppComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        context = this
        appComponent = DaggerAppComponent.builder()
            .build()
    }

    companion object {
        lateinit var context: Context
            private set
    }
}