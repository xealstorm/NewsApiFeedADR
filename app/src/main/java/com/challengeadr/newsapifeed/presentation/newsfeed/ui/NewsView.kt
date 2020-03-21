package com.challengeadr.newsapifeed.presentation.newsfeed.ui

import androidx.paging.PagedList
import com.challengeadr.newsapifeed.presentation.base.ui.BaseView
import com.challengeadr.newsapifeed.presentation.newsfeed.model.NewsItem

interface NewsView : BaseView {
    fun updateNewsWithData(pagedList: PagedList<NewsItem>)

    fun setRefreshingTo(value: Boolean)
}