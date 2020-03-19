package com.challengeadr.newsapifeed.di.activity

import com.challengeadr.newsapifeed.di.app.AppComponent
import com.challengeadr.newsapifeed.di.presenter.PresenterModule
import com.challengeadr.newsapifeed.presentation.newsfeed.ui.NewsActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ActivityModule::class, PresenterModule::class]
)
interface ActivityComponent {
    fun injectTo(activity: NewsActivity)
}