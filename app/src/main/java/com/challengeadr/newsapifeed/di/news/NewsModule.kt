package com.challengeadr.newsapifeed.di.news

import com.challengeadr.newsapifeed.presentation.newsfeed.ui.NewsAdapter
import dagger.Module
import dagger.Provides

@Module
class NewsModule {
    @NewsScope
    @Provides
    fun provideNewsAdapter(): NewsAdapter {
        return NewsAdapter()
    }
}