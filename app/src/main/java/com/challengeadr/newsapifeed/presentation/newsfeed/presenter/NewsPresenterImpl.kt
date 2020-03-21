package com.challengeadr.newsapifeed.presentation.newsfeed.presenter

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.paging.DataSource
import androidx.paging.RxPagedListBuilder
import com.challengeadr.newsapifeed.presentation.newsfeed.model.NewsDataSourceFactory
import com.challengeadr.newsapifeed.presentation.newsfeed.model.NewsItem
import com.challengeadr.newsapifeed.presentation.newsfeed.ui.NewsView
import com.challengeadr.newsapifeed.util.scedulers.SchedulerProvider
import io.reactivex.disposables.Disposables

class NewsPresenterImpl(
    private val newsDataSourceFactory: DataSource.Factory<Int, NewsItem>,
    private val schedulerProvider: SchedulerProvider
) : NewsPresenter {
    private var view: NewsView? = null
    private var disposable = Disposables.empty()

    private val pagedListObservable by lazy {
        RxPagedListBuilder<Int, NewsItem>(
            newsDataSourceFactory,
            NewsDataSourceFactory.providePagingConfig()
        )
            .setFetchScheduler(schedulerProvider.ui())
            .setNotifyScheduler(schedulerProvider.ui())
            .buildObservable()
    }

    override fun setView(view: NewsView) {
        this.view = view
    }

    override fun provideNews() {
        disposable = pagedListObservable
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ pagedList ->
                view?.updateNewsWithData(pagedList)
            }, { t ->
                Log.e(TAG, t.toString())
            })
    }

    @VisibleForTesting
    internal fun getView(): NewsView? = view

    override fun dispose() {
        disposable.dispose()
    }

    companion object {
        val TAG = NewsPresenterImpl::class.java.toString()
    }
}