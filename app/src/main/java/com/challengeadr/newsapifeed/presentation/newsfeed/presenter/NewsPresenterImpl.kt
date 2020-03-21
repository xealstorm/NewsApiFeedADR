package com.challengeadr.newsapifeed.presentation.newsfeed.presenter

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.paging.DataSource
import androidx.paging.RxPagedListBuilder
import com.challengeadr.newsapifeed.db.repository.NewsRepository
import com.challengeadr.newsapifeed.network.Configuration
import com.challengeadr.newsapifeed.presentation.newsfeed.model.NewsDataSourceFactory
import com.challengeadr.newsapifeed.presentation.newsfeed.model.NewsItem
import com.challengeadr.newsapifeed.presentation.newsfeed.ui.NewsView
import com.challengeadr.newsapifeed.util.scedulers.SchedulerProvider
import io.reactivex.disposables.Disposables

class NewsPresenterImpl(
    private val newsDataSourceFactory: DataSource.Factory<Int, NewsItem>,
    private val schedulerProvider: SchedulerProvider,
    private val newsRepository: NewsRepository
) : NewsPresenter {
    private var view: NewsView? = null
    private var disposable = Disposables.empty()
    private var itemsInitial = Configuration.DEFAULT_ITEMS_INITIAL_NUMBER

    private val pagedListObservable by lazy {
        RxPagedListBuilder<Int, NewsItem>(
            newsDataSourceFactory,
            NewsDataSourceFactory.providePagingConfig(itemsInitial)
        )
            .setFetchScheduler(schedulerProvider.ui())
            .setNotifyScheduler(schedulerProvider.ui())
            .buildObservable()
    }

    override fun setView(view: NewsView) {
        this.view = view
    }

    /**
     * Initializes a paged list and translates it to the view
     * @param itemsToRequest - an amount of items to be requested initially
     */
    override fun provideNews(itemsToRequest: Int?) {
        if (itemsToRequest != null && itemsToRequest > 0) {
            itemsInitial = itemsToRequest
        }
        disposable = pagedListObservable
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ pagedList ->
                view?.updateNewsWithData(pagedList)
                view?.setRefreshingTo(false)
            }, { t ->
                Log.e(TAG, t.toString())
                view?.setRefreshingTo(false)
            })
    }

    @VisibleForTesting
    internal fun getView(): NewsView? = view

    override fun removeNews() {
        newsRepository.cleanItems()
    }

    override fun dispose() {
        disposable.dispose()
    }

    companion object {
        val TAG = NewsPresenterImpl::class.java.toString()
    }
}