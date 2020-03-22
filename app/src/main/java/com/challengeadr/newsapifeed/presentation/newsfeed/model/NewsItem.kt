package com.challengeadr.newsapifeed.presentation.newsfeed.model

import android.content.Context
import com.challengeadr.newsapifeed.util.format.TimeFormatter

data class NewsItem(
    val sourceName: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: Long,
    val content: String
) {
    fun getPublishedDateFormatted(context: Context) =
        TimeFormatter.dateFormatted(context, publishedAt)

    companion object {
        fun create(
            sourceName: String?,
            title: String?,
            description: String?,
            url: String?,
            urlToImage: String?,
            publishedAt: Long,
            content: String?
        ): NewsItem {
            return NewsItem(
                sourceName ?: "",
                title ?: "",
                description ?: "",
                url ?: "",
                urlToImage ?: "",
                publishedAt,
                content ?: ""
            )
        }
    }
}