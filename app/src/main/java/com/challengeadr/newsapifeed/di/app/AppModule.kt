package com.challengeadr.newsapifeed.di.app

import com.challengeadr.newsapifeed.App
import com.challengeadr.newsapifeed.db.RealmCreator
import com.challengeadr.newsapifeed.util.scedulers.AppSchedulerProvider
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {
    @Provides
    @Singleton
    fun provideRealm(app: App): Realm {
        return RealmCreator.create(app.applicationContext)
    }

    @Provides
    @Singleton
    fun provideSchedulerProvider() = AppSchedulerProvider()
}