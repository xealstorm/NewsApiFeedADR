package com.challengeadr.newsapifeed.di.app

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, NetworkServicesModule::class]
)
interface AppComponent {
}