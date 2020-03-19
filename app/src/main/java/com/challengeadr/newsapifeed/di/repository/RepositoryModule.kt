package com.challengeadr.newsapifeed.di.repository

import com.challengeadr.newsapifeed.db.repository.NewsRepository
import com.challengeadr.newsapifeed.db.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideNewsRepository(realm: Realm): NewsRepository {
        return NewsRepositoryImpl(realm)
    }
}
