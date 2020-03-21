package com.challengeadr.newsapifeed.db

import com.challengeadr.newsapifeed.db.model.NewsModel
import com.challengeadr.newsapifeed.db.model.NewsTimestampModel
import io.realm.annotations.RealmModule

@RealmModule(
    library = true,
    classes = [NewsModel::class, NewsTimestampModel::class]
)
class RealmModule
