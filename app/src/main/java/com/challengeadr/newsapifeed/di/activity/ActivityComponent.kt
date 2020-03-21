package com.challengeadr.newsapifeed.di.activity

import com.challengeadr.newsapifeed.di.app.AppComponent
import com.challengeadr.newsapifeed.di.news.NewsComponent
import com.challengeadr.newsapifeed.di.news.NewsModule
import com.challengeadr.newsapifeed.di.presenter.PresenterModule
import dagger.Component

@ActivityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ActivityModule::class, PresenterModule::class]
)
interface ActivityComponent {
    fun plus(ratesModule: NewsModule): NewsComponent
}