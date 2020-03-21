package com.challengeadr.newsapifeed.presentation.newsfeed.model

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.challengeadr.newsapifeed.network.Configuration
import com.challengeadr.newsapifeed.presentation.newsfeed.provider.NewsProvider


class NewsDataSource(
    private val newsProvider: NewsProvider
) : PageKeyedDataSource<Int, NewsItem>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, NewsItem>
    ) {
        newsProvider.getItems({ newsItems ->
            Log.i(TAG, newsItems.toString())
            callback.onResult(newsItems, null, 4)
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
        newsProvider.getItems(
            params.key,
            params.requestedLoadSize,
            Configuration.COUNTRY_CODE_OF_SOURCES,
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

    companion object {
        private val TAG = NewsDataSource::class.java.simpleName
    }
}