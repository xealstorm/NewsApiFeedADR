package com.challengeadr.newsapifeed.provider

import com.challengeadr.newsapifeed.presentation.newsfeed.model.NewsItem

interface NewsProvider {
    fun getItemsInitial(
        itemsToRequest: Int,
        onSuccess: (newsItems: List<NewsItem>) -> Unit,
        onError: (t: Throwable?) -> Unit
    )

    fun getItemsAfter(
        page: Int,
        pageSize: Int,
        onSuccess: (newsItems: List<NewsItem>) -> Unit,
        onError: (t: Throwable?) -> Unit
    )
}