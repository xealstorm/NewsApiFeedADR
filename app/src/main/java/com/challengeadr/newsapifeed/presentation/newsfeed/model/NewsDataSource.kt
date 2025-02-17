package com.challengeadr.newsapifeed.presentation.newsfeed.model

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.challengeadr.newsapifeed.network.Configuration
import com.challengeadr.newsapifeed.provider.NewsProvider

class NewsDataSource(
    private val newsProvider: NewsProvider
) : PageKeyedDataSource<Int, NewsItem>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, NewsItem>
    ) {
        newsProvider.getItemsInitial(
            params.requestedLoadSize, { newsItems ->
                Log.i(TAG, newsItems.toString())
                callback.onResult(
                    newsItems,
                    null,
                    provideNextPage(newsItems.size)
                )
            }, { t ->
                Log.e(TAG, t.toString())
            })
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, NewsItem>
    ) {
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, NewsItem>
    ) {
        newsProvider.getItemsAfter(
            params.key,
            params.requestedLoadSize,
            { newsItems ->
                Log.i(TAG, newsItems.toString())
                val nextKey: Int? =
                    if (newsItems.isNullOrEmpty()) {
                        null
                    } else {
                        params.key + 1
                    }
                callback.onResult(newsItems, nextKey)
            }, { t ->
                Log.e(TAG, t.toString())
            })
    }

    private fun provideNextPage(itemsSize: Int) =
        itemsSize / Configuration.DEFAULT_ITEMS_PER_PAGE_NUMBER +
                if (itemsSize % Configuration.DEFAULT_ITEMS_PER_PAGE_NUMBER == 0) {
                    1
                } else {
                    2
                }

    companion object {
        private val TAG = NewsDataSource::class.java.simpleName
    }
}