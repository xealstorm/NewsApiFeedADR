package com.challengeadr.newsapifeed.db.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class NewsTimestampModel(
    @PrimaryKey
    var receivedAt: Long = 0L,
    var weight: Int = 1
) : RealmObject() {
    companion object {
        const val RECEIVED_AT_FIELD = "receivedAt"
        const val WEIGHT_FIELD = "weight"
    }
}