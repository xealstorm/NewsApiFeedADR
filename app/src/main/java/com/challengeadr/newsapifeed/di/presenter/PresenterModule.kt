package com.challengeadr.newsapifeed.di.presenter

import com.challengeadr.newsapifeed.di.activity.ActivityScope
import com.challengeadr.newsapifeed.presentation.newsfeed.presenter.NewsPresenter
import com.challengeadr.newsapifeed.presentation.newsfeed.presenter.NewsPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
    @Provides
    @ActivityScope
    fun provideNewsPresenter(): NewsPresenter {
        return NewsPresenterImpl()
    }
}