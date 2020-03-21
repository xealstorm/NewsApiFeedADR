package com.challengeadr.newsapifeed.presentation.newsfeed.presenter

import com.challengeadr.newsapifeed.presentation.base.presenter.BasePresenter
import com.challengeadr.newsapifeed.presentation.newsfeed.ui.NewsView

interface NewsPresenter : BasePresenter<NewsView> {
    fun provideNews()

    fun dispose()
}