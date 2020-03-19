package com.challengeadr.newsapifeed.di.app

import com.challengeadr.newsapifeed.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {
}