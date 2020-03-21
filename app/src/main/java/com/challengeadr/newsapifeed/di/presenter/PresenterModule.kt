package com.challengeadr.newsapifeed.di.presenter

import androidx.paging.DataSource
import com.challengeadr.newsapifeed.di.activity.ActivityScope
import com.challengeadr.newsapifeed.presentation.newsfeed.model.NewsItem
import com.challengeadr.newsapifeed.presentation.newsfeed.presenter.NewsPresenter
import com.challengeadr.newsapifeed.presentation.newsfeed.presenter.NewsPresenterImpl
import com.challengeadr.newsapifeed.util.scedulers.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
    @Provides
    @ActivityScope
    fun provideNewsPresenter(
        newsDataSourceFactory: DataSource.Factory<Int, NewsItem>,
        schedulerProvider: SchedulerProvider
    ): NewsPresenter {
        return NewsPresenterImpl(newsDataSourceFactory, schedulerProvider)
    }
}