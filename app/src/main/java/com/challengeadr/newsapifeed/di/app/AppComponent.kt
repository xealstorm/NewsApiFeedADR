package com.challengeadr.newsapifeed.di.app

import androidx.paging.DataSource
import com.challengeadr.newsapifeed.db.repository.NewsRepository
import com.challengeadr.newsapifeed.di.datasource.DataSourceFactoryModule
import com.challengeadr.newsapifeed.di.network.NetworkServicesModule
import com.challengeadr.newsapifeed.di.provider.ProviderModule
import com.challengeadr.newsapifeed.di.repository.RepositoryModule
import com.challengeadr.newsapifeed.presentation.newsfeed.model.NewsItem
import com.challengeadr.newsapifeed.util.scedulers.SchedulerProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
        NetworkServicesModule::class,
        RepositoryModule::class,
        DataSourceFactoryModule::class,
        ProviderModule::class]
)
interface AppComponent {

    fun schedulerProvider(): SchedulerProvider

    fun provideDataSourceFactory(): DataSource.Factory<Int, NewsItem>

    fun provideNewsRepository(): NewsRepository
}