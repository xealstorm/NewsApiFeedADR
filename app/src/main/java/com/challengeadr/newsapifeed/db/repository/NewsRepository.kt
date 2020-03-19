package com.challengeadr.newsapifeed.db.repository

import com.challengeadr.newsapifeed.db.model.NewsModel
import io.realm.RealmResults

interface NewsRepository {
    fun cleanItems()

    fun addOrUpdateItem(
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

    fun getItems(): RealmResults<NewsModel>
}