package com.challengeadr.newsapifeed.di.news

import com.challengeadr.newsapifeed.presentation.newsfeed.ui.NewsActivity
import dagger.Subcomponent

@NewsScope
@Subcomponent(modules = [NewsModule::class])
interface NewsComponent {
    fun injectTo(activity: NewsActivity)
}