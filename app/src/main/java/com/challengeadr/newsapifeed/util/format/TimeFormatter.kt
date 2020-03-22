package com.challengeadr.newsapifeed.util.format

import android.content.Context
import com.challengeadr.newsapifeed.R
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Hours
import org.joda.time.Minutes
import org.joda.time.format.DateTimeFormat

object TimeFormatter {
    private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"

    @Throws(IllegalArgumentException::class)
    fun dateDeFormatted(date: String?): DateTime? {
        return DateTime.parse(date, DateTimeFormat.forPattern(DATE_FORMAT))
    }

    fun dateFormatted(context:Context, timestamp: Long) : String {
        var timeString = ""
        val createdDateTime = DateTime(timestamp)

        val currentTime = DateTime.now()
        val isCurrentYear = createdDateTime.year == currentTime.year
        val daysPassed = Days.daysBetween(createdDateTime, currentTime).days
        val hoursPassed = Hours.hoursBetween(createdDateTime, currentTime).hours
        val minutesPassed = Minutes.minutesBetween(createdDateTime, currentTime).minutes
        timeString = when {
            (daysPassed >= 7) && isCurrentYear.not() -> context.getString(R.string.date_more_than_a_year)
            hoursPassed >= 24 -> String.format(context.getString(R.string.date_days_ago), daysPassed)
            minutesPassed >= 60 -> String.format(context.getString(R.string.date_hours_ago), hoursPassed)
            minutesPassed > 0 -> String.format(context.getString(R.string.date_minutes_ago), minutesPassed)
            else -> context.getString(R.string.date_now)
        }
        return timeString
    }
}