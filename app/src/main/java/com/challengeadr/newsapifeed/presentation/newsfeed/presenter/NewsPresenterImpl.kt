package com.challengeadr.newsapifeed.presentation.newsfeed.presenter

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.challengeadr.newsapifeed.db.repository.NewsRepository
import com.challengeadr.newsapifeed.network.NetworkService
import com.challengeadr.newsapifeed.network.model.ItemsResponse
import com.challengeadr.newsapifeed.presentation.newsfeed.ui.NewsView
import com.challengeadr.newsapifeed.util.format.TimeFormatter
import com.challengeadr.newsapifeed.util.scedulers.SchedulerProvider
import org.joda.time.DateTime
import java.lang.IllegalArgumentException

class NewsPresenterImpl(
    private val networkService: NetworkService,
    private val newsRepository: NewsRepository,
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
                saveDataLocally(itemsResponse, DateTime.now().millis)
            }, { t: Throwable? ->
                Log.e(TAG, t.toString())
            })
    }

    private fun saveDataLocally(itemsResponse: ItemsResponse?, itemsReceivedAt: Long) {
        if (itemsResponse?.articles != null) {
            itemsResponse.articles.filterNotNull().forEach { article ->
                newsRepository.addOrUpdateItem(
                    article.author,
                    article.title,
                    article.description,
                    article.url,
                    article.urlToImage,
                    if (article.publishedAt.isNullOrEmpty()) {
                        0L
                    } else {
                        try {
                            TimeFormatter.dateDeFormatted(article.publishedAt)?.millis ?: 0L
                        } catch (e: IllegalArgumentException) {
                            0L
                        }
                    },
                    article.content,
                    article.source?.id,
                    article.source?.name,
                    itemsReceivedAt
                )
            }
        }
    }
    @VisibleForTesting
    internal fun getView(): NewsView? = view

    companion object {
        val TAG = NewsPresenterImpl::class.java.toString()
    }
}