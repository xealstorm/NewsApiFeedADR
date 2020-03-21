package com.challengeadr.newsapifeed.di.provider

import com.challengeadr.newsapifeed.db.repository.NewsRepository
import com.challengeadr.newsapifeed.network.NetworkService
import com.challengeadr.newsapifeed.provider.NewsProvider
import com.challengeadr.newsapifeed.provider.NewsProviderImpl
import com.challengeadr.newsapifeed.util.scedulers.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProviderModule {
    @Provides
    @Singleton
    fun provideNewsProvider(
        newsRepository: NewsRepository,
        networkService: NetworkService,
        schedulerProvider: SchedulerProvider
    ): NewsProvider {
        return NewsProviderImpl(
            newsRepository, networkService, schedulerProvider
        )
    }
}