package com.challengeadr.newsapifeed

import android.app.Application
import android.content.Context
import com.challengeadr.newsapifeed.di.app.AppComponent
import com.challengeadr.newsapifeed.di.app.AppModule
import com.challengeadr.newsapifeed.di.app.DaggerAppComponent
import com.challengeadr.newsapifeed.di.network.NetworkServicesModule

class App : Application() {

    var appComponent: AppComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        context = this
        appComponent = DaggerAppComponent.builder()
            .networkServicesModule(NetworkServicesModule(BuildConfig.NEWS_ORG_ENDPOINT))
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        lateinit var context: Context
            private set
    }
}