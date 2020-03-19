package com.challengeadr.newsapifeed.di.app

import com.challengeadr.newsapifeed.util.scedulers.AppSchedulerProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, NetworkServicesModule::class]
)
interface AppComponent {

    fun schedulerProvider(): AppSchedulerProvider

}