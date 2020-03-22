package com.challengeadr.newsapifeed.provider

import android.util.Log
import com.challengeadr.newsapifeed.App
import com.challengeadr.newsapifeed.db.model.NewsModel
import com.challengeadr.newsapifeed.db.repository.NewsRepository
import com.challengeadr.newsapifeed.network.Configuration
import com.challengeadr.newsapifeed.network.NetworkService
import com.challengeadr.newsapifeed.network.model.ItemsResponse
import com.challengeadr.newsapifeed.presentation.newsfeed.model.NewsItem
import com.challengeadr.newsapifeed.util.format.TimeFormatter
import com.challengeadr.newsapifeed.util.scedulers.SchedulerProvider
import io.realm.RealmResults
import org.joda.time.DateTime
import java.lang.IllegalArgumentException
import java.util.*

class NewsProviderImpl(
    private val newsRepository: NewsRepository,
    private val networkService: NetworkService,
    private val schedulerProvider: SchedulerProvider
) : NewsProvider {
    /**
     * Gets the initial list of the news items
     * @param itemsToRequest - an amount of items to request
     * @param onSuccess - a method that handles the list of news items once it's received
     * @param onError - a method that handles the error thrown
     */
    override fun getItemsInitial(
        itemsToRequest: Int,
        onSuccess: (newsItems: List<NewsItem>) -> Unit,
        onError: (t: Throwable?) -> Unit
    ) {
        val realmNewsModels = newsRepository.getItemsInitialOrNull(itemsToRequest)
        if (realmNewsModels.isNullOrEmpty()) {
            requestFromApiAndPersistLocally(1, itemsToRequest)
                .subscribe({ newsItems ->
                    onSuccess(newsItems)
                }, { t: Throwable? ->
                    Log.e(TAG, t.toString())
                    onError(t)
                })
        } else {
            val newsItems = fromRealmResults(realmNewsModels)
            onSuccess(newsItems)
        }
    }

    /**
     * Gets the list of the news items
     * @param page - an index of a page to request
     * @param pageSize – an amount of items for the page to request
     * @param onSuccess - a method that handles the list of news items once it's received
     * @param onError - a method that handles the error thrown
     */
    override fun getItemsAfter(
        page: Int,
        pageSize: Int,
        onSuccess: (newsItems: List<NewsItem>) -> Unit,
        onError: (t: Throwable?) -> Unit
    ) {
        val realmNewsModels = newsRepository.getItemsOrNull(page, pageSize)
        if (realmNewsModels.isNullOrEmpty()) {
            requestFromApiAndPersistLocally(page, pageSize)
                .subscribe({ newsItems ->
                    onSuccess(newsItems)
                }, { t: Throwable? ->
                    Log.e(TAG, t.toString())
                    onError(t)
                })
        } else {
            val newsItems = fromRealmResults(realmNewsModels)
            onSuccess(newsItems)
        }
    }

    private fun fromRealmResults(realmNewsModels: RealmResults<NewsModel>) =
        realmNewsModels.map {
            NewsItem.create(
                it.sourceName,
                it.title,
                it.description,
                it.url,
                it.urlToImage,
                it.publishedAt,
                it.content
            )
        }

    /**
     * Requests the items from the API and persists in a local DB
     * @param page - an index of a page to request
     * @param pageSize – an amount of items for the page to request
     */
    private fun requestFromApiAndPersistLocally(page: Int, pageSize: Int) =
        networkService.getItems(
            page,
            pageSize,
            getCountryCode(App.context.resources.configuration.locale.language)
        )
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .map { itemsResponse ->
                saveDataLocally(itemsResponse, DateTime.now().millis, pageSize)
                newsRepository.getItemsOrNull(page, pageSize)
            }
            .map { newsModels ->
                if (newsModels.isNullOrEmpty()) {
                    listOf<NewsItem>()
                } else {
                    newsModels.map {
                        NewsItem.create(
                            it.sourceName,
                            it.title,
                            it.description,
                            it.url,
                            it.urlToImage,
                            it.publishedAt,
                            it.content
                        )
                    }
                }
            }

    /**
     * Saves the articles from the API response to the according table of the DB.
     * Each article has a timestamp indicating when it was received.
     * Every new timestamp is also put into a Timestamp table in the DB.
     *
     * @param itemsResponse – a response got from the API with articles inside
     * @param itemsReceivedAt - a timestamp indicating the time the itemsResponse was fetched at
     * @param pageSize - an amount of articles requested for this page
     */
    internal fun saveDataLocally(
        itemsResponse: ItemsResponse?,
        itemsReceivedAt: Long,
        pageSize: Int
    ) {
        if (itemsResponse?.articles != null) {
            if (itemsResponse.articles.filterNotNull().isNotEmpty()) {
                newsRepository.addTimestamp(
                    itemsReceivedAt,
                    pageSize / Configuration.DEFAULT_ITEMS_PER_PAGE_NUMBER
                )
                itemsResponse.articles.filterNotNull().forEach { article ->
                    newsRepository.addOrUpdateNewsModel(
                        article.author,
                        article.title,
                        article.description,
                        article.url,
                        article.urlToImage,
                        if (article.publishedAt.isNullOrEmpty()) {
                            0L
                        } else {
                            try {
                                TimeFormatter.dateDeFormatted(article.publishedAt)?.millis
                                    ?: 0L
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
    }

    internal fun getCountryCode(language: String): String {
        val code = language.substringAfterLast('-')
        return if (Locale.getISOCountries().any { it.equals(code, true) }) {
            code
        } else {
            Configuration.COUNTRY_CODE_OF_SOURCES
        }.toLowerCase()
    }

    companion object {
        val TAG = NewsProviderImpl::class.java.toString()
    }
}