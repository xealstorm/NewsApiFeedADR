package com.challengeadr.newsapifeed.db.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class NewsModel(
    @PrimaryKey
    var id: Int? = 0,
    var author: String? = "",
    var title: String? = "",
    var description: String? = "",
    var url: String? = "",
    var urlToImage: String? = "",
    var publishedAt: Long = 0L,
    var content: String? = "",
    var sourceId: String? = "",
    var sourceName: String? = "",
    var receivedAt:Long = 0L
) : RealmObject() {
    companion object {
        const val RECEIVED_AT_FIELD = "receivedAt"
    }
}