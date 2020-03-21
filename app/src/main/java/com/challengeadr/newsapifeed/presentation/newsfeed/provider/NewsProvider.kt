package com.challengeadr.newsapifeed.presentation.newsfeed.provider

import com.challengeadr.newsapifeed.network.Configuration
import com.challengeadr.newsapifeed.presentation.newsfeed.model.NewsItem

interface NewsProvider {
    fun getItems(
        page: Int,
        pageSize: Int,
        countryCode: String,
        onSuccess: (newsItems: List<NewsItem>) -> Unit,
        onError: (t: Throwable?) -> Unit
    )

    fun getItems(
        onSuccess: (newsItems: List<NewsItem>) -> Unit,
        onError: (t: Throwable?) -> Unit
    ) = getItems(
        Configuration.DEFAULT_PAGE_NUMBER,
        Configuration.DEFAULT_ITEMS_INITIAL_NUMBER,
        Configuration.COUNTRY_CODE_OF_SOURCES,
        onSuccess,
        onError
    )
}