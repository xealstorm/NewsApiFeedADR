package com.challengeadr.newsapifeed.db.repository

import com.challengeadr.newsapifeed.db.model.NewsModel
import io.realm.Realm
import io.realm.RealmResults
import java.util.*

class NewsRepositoryImpl(private val realm: Realm) : NewsRepository {

    override fun cleanItems() {
        realm.executeTransaction {
            val result = it.where(NewsModel::class.java)
                .findAll()
            result.deleteAllFromRealm()
        }
    }

    override fun addOrUpdateItem(
        author: String?,
        title: String?,
        description: String?,
        url: String?,
        urlToImage: String?,
        publishedAt: Long,
        content: String?,
        sourceId: String?,
        sourceName: String?,
        receivedAt: Long
    ) {
        realm.executeTransaction {
            it.insertOrUpdate(
                NewsModel(
                    Objects.hash(author, title, url),
                    author,
                    title,
                    description,
                    url,
                    urlToImage,
                    publishedAt,
                    content,
                    sourceId,
                    sourceName,
                    receivedAt
                )
            )
        }
    }

    override fun getItems(): RealmResults<NewsModel> {
        val result = realm.where(NewsModel::class.java)
            .findAll()
        return result
    }
}