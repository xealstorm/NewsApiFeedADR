package com.challengeadr.newsapifeed.network.model

import com.google.gson.annotations.SerializedName

data class ItemsResponse(
    val status: String?,
    @SerializedName("totalResults")
    val totalResults: Int?,
    val articles: List<Article?>?
)

data class Article(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    val content: String?
)

data class Source(
    val id: String?,
    val name: String?
)