package com.challengeadr.newsapifeed.db.repository

import com.challengeadr.newsapifeed.db.model.NewsModel
import com.challengeadr.newsapifeed.db.model.NewsTimestampModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import java.util.*

class NewsRepositoryImpl(private val realm: Realm) : NewsRepository {

    /**
     * Deletes all the items from the news table and timestamps table
     */
    override fun cleanItems() {
        realm.executeTransaction { realm ->
            deleteNews(realm)
            deleteTimestamps(realm)
        }
    }

    /**
     * Adds a timestamp to the DB
     * @param receivedAt - a timestamp
     * @param weight - a weight of the timestamp
     */
    override fun addTimestamp(receivedAt: Long, weight: Int) {
        realm.executeTransaction {
            it.insertOrUpdate(
                NewsTimestampModel(receivedAt, weight)
            )
        }
    }

    /**
     * Adds or updates a news model
     */
    override fun addOrUpdateNewsModel(
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

    /**
     * Gets news models by page and the items per page
     * @param page - an index of a page to request
     * @param itemsLimit - an amount of items to request
     */
    override fun getItemsOrNull(page: Int, itemsLimit: Int): RealmResults<NewsModel>? {
        val timestampResults = realm.where(NewsTimestampModel::class.java)
            .sort(NewsTimestampModel.RECEIVED_AT_FIELD, Sort.ASCENDING)
            .findAll()
        val timestampForPage = getValidTimeStampOrNull(timestampResults, page)
        return if (timestampForPage != null) {
            getItemsByTimestampOrNull(timestampForPage, itemsLimit)
        } else {
            null
        }
    }

    /**
     * Returns a valid timestamp for the page
     *
     * Each timestamp record has a weight field indicating how many of the base pages it holds.
     * For example the initial page contains 21 element (3*7) - so the weight this timestamp is 3
     * Every next page contains 7 (or less) elements (1*7) - so the weight this timestamp is 1
     * To identify a corresponding timestamp based on page we need to summarize the weights until
     * they are equal or more than the page value
     *
     * This is done for not storing the actual page number in the database since it's a subject of change
     * This solution works for any sequence of requests: Y*X items, 1*X items, 1*X items, etc - where Y is a multiplier
     * for the initial request and X is a fixed amount of items per page
     *
     * @param timestampResults - list of timestamps ASC-sorted
     * @param page - an index of a page to request
     */
    private fun getValidTimeStampOrNull(
        timestampResults: RealmResults<NewsTimestampModel>?,
        page: Int
    ): Long? {
        return if (timestampResults != null) {
            var i = 0
            var isValid = false
            var weight = 0
            while (i < timestampResults.size && !isValid) {
                weight += timestampResults[i]!!.weight
                isValid = weight >= page
                i++
            }
            if (isValid) {
                timestampResults[i - 1]?.receivedAt
            } else {
                null
            }
        } else {
            null
        }
    }

    /**
     * Returns items by timestamp
     * @param receivedAt - a timestamp to get the news according to
     * @param itemsLimit - a number of news items to return
     */
    private fun getItemsByTimestampOrNull(
        receivedAt: Long?,
        itemsLimit: Int
    ): RealmResults<NewsModel>? {
        return if (receivedAt != null) {
            realm.where(NewsModel::class.java)
                .equalTo(NewsModel.RECEIVED_AT_FIELD, receivedAt)
                .limit(itemsLimit.toLong())
                .findAll()
        } else {
            null
        }
    }

    /**
     * Clears the news table
     */
    private fun deleteNews(realm: Realm) {
        val result = realm.where(NewsModel::class.java)
            .findAll()
        result.deleteAllFromRealm()
    }

    /**
     * Clears the timestamps table
     */
    private fun deleteTimestamps(realm: Realm) {
        val result = realm.where(NewsTimestampModel::class.java)
            .findAll()
        result.deleteAllFromRealm()
    }
}