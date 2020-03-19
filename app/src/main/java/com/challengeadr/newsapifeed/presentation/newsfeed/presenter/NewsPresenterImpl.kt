package com.challengeadr.newsapifeed.presentation.newsfeed.presenter

import androidx.annotation.VisibleForTesting
import com.challengeadr.newsapifeed.presentation.newsfeed.ui.NewsView

class NewsPresenterImpl(
) : NewsPresenter {
    private var view: NewsView? = null

    override fun setView(view: NewsView) {
        this.view = view
    }


    @VisibleForTesting
    internal fun getView(): NewsView? = view
}