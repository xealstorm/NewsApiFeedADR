package com.challengeadr.newsapifeed.util.network

import com.challengeadr.newsapifeed.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AuthorizationHeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", BuildConfig.NEWS_ORG_API_KEY)
            .build()
        return chain.proceed(newRequest)
    }
}