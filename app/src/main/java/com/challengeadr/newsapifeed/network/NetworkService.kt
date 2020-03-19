package com.challengeadr.newsapifeed.network

import com.challengeadr.newsapifeed.network.model.ItemsResponse
import io.reactivex.Single

interface NetworkService {
    fun getItems(
        page: Int = Configuration.DEFAULT_PAGE_NUMBER,
        pageSize: Int = Configuration.DEFAULT_ITEMS_INITIAL_NUMBER,
        countryCode: String = Configuration.COUNTRY_CODE_OF_SOURCES
    ): Single<ItemsResponse>
}