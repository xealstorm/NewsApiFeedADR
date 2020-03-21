package com.challengeadr.newsapifeed.presentation.newsfeed.model

import org.joda.time.DateTime

data class NewsItem(
    val sourceName: String,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: DateTime,
    val content: String
) {
    companion object {
        fun create(
            sourceName: String?,
            author: String?,
            title: String?,
            description: String?,
            url: String?,
            urlToImage: String?,
            publishedAt: Long,
            content: String?
        ): NewsItem {
            return NewsItem(
                sourceName ?: "",
                author ?: "",
                title ?: "",
                description ?: "",
                url ?: "",
                urlToImage ?: "",
                DateTime.now().withMillis(publishedAt),
                content ?: ""
            )
        }
    }
}