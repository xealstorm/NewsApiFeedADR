package com.challengeadr.newsapifeed.presentation.base.presenter

import com.challengeadr.newsapifeed.presentation.base.ui.BaseView

interface BasePresenter<T : BaseView> {
    fun setView(view: T)
}