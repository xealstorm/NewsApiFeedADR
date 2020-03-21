package com.challengeadr.newsapifeed.di.datasource

import androidx.paging.DataSource
import com.challengeadr.newsapifeed.presentation.newsfeed.model.NewsDataSourceFactory
import com.challengeadr.newsapifeed.presentation.newsfeed.model.NewsItem
import com.challengeadr.newsapifeed.presentation.newsfeed.provider.NewsProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceFactoryModule {
    @Provides
    @Singleton
    fun provideNewsDataSource(
        newsProvider: NewsProvider
    ): DataSource.Factory<Int, NewsItem> {
        return NewsDataSourceFactory(newsProvider)
    }
}