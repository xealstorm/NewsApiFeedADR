package com.challengeadr.newsapifeed.network

import com.challengeadr.newsapifeed.network.model.ItemsResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

class NetworkServiceImpl(retrofit: Retrofit) : NetworkService {

    internal val api = retrofit.create<Api>(Api::class.java)

    override fun getItems(page: Int, pageSize: Int, countryCode: String): Single<ItemsResponse> =
        api.getItems(page, pageSize, countryCode)

    internal interface Api {
        @GET("/v2/top-headlines")
        fun getItems(
            @Query("page") page: Int,
            @Query("pageSize") pageSize: Int,
            @Query("country") countryCode: String
        ): Single<ItemsResponse>
    }

}