package com.challengeadr.newsapifeed.db

import com.challengeadr.newsapifeed.db.model.NewsModel
import io.realm.annotations.RealmModule

@RealmModule(
    library = true,
    classes = [NewsModel::class]
)
class RealmModule
