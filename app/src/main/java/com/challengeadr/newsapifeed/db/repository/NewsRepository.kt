package com.challengeadr.newsapifeed.db.repository

import com.challengeadr.newsapifeed.db.model.NewsModel
import io.realm.RealmResults

interface NewsRepository {
    fun cleanItems()

    fun addTimestamp(
        receivedAt: Long = 0L,
        weight: Int = 1
    )

    fun addOrUpdateNewsModel(
        author: String? = "",
        title: String? = "",
        description: String? = "",
        url: String? = "",
        urlToImage: String? = "",
        publishedAt: Long = 0L,
        content: String? = "",
        sourceId: String? = "",
        sourceName: String? = "",
        receivedAt: Long = 0L
    )

    fun getItemsOrNull(page: Int, itemsLimit: Int): RealmResults<NewsModel>?
}