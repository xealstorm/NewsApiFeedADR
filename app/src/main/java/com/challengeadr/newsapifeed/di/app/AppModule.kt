package com.challengeadr.newsapifeed.di.app

import com.challengeadr.newsapifeed.App
import com.challengeadr.newsapifeed.util.scedulers.AppSchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {
    @Provides
    @Singleton
    fun provideSchedulerProvider() = AppSchedulerProvider()
}