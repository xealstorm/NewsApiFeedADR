package com.challengeadr.newsapifeed.network

object Configuration {
    /**
     * The number of the first page to request
     */
    const val DEFAULT_PAGE_NUMBER = 1
    /**
     * The number of the items to request initially
     */
    const val DEFAULT_ITEMS_INITIAL_NUMBER = 21
    /**
     * The number of the items per page to request
     */
    const val DEFAULT_ITEMS_PER_PAGE_NUMBER = 7
    /**
     * The 2-letters country code of the news sources
     */
    const val COUNTRY_CODE_OF_SOURCES = "us"
}