package com.challengeadr.newsapifeed.util.format

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

object TimeFormatter {
    private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"

    @Throws(IllegalArgumentException::class)
    fun dateDeFormatted(date: String?): DateTime? {
        return DateTime.parse(date, DateTimeFormat.forPattern(DATE_FORMAT))
    }
}