package com.challengeadr.newsapifeed.presentation.newsfeed.presenter

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.challengeadr.newsapifeed.network.NetworkService
import com.challengeadr.newsapifeed.network.model.ItemsResponse
import com.challengeadr.newsapifeed.presentation.newsfeed.ui.NewsView
import com.challengeadr.newsapifeed.util.scedulers.SchedulerProvider

class NewsPresenterImpl(
    private val networkService: NetworkService,
    private val schedulerProvider: SchedulerProvider
) : NewsPresenter {
    private var view: NewsView? = null

    override fun setView(view: NewsView) {
        this.view = view
    }

    override fun provideNews() {
        networkService.getItems()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ itemsResponse: ItemsResponse? ->
                Log.i(TAG, itemsResponse.toString())
            }, { t: Throwable? ->
                Log.e(TAG, t.toString())
            })
    }

    @VisibleForTesting
    internal fun getView(): NewsView? = view

    companion object{
        val TAG = NewsPresenterImpl::class.java.toString()
    }
}