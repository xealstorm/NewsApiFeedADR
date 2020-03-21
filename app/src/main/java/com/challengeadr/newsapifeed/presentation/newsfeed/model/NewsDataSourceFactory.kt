package com.challengeadr.newsapifeed.presentation.newsfeed.model

import androidx.paging.DataSource
import androidx.paging.PagedList
import com.challengeadr.newsapifeed.network.Configuration
import com.challengeadr.newsapifeed.presentation.newsfeed.provider.NewsProvider

class NewsDataSourceFactory(
    private val newsProvider: NewsProvider
) : DataSource.Factory<Int, NewsItem>() {
    private var feedDataSource: DataSource<Int, NewsItem>? = null
    override fun create(): DataSource<Int, NewsItem> {
        feedDataSource = NewsDataSource(newsProvider)
        return feedDataSource!!
    }

    companion object {
        fun providePagingConfig(): PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(Configuration.DEFAULT_ITEMS_INITIAL_NUMBER)
            .setPageSize(Configuration.DEFAULT_ITEMS_PER_PAGE_NUMBER)
            .build()
    }
}