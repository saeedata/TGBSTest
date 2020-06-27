package org.technical.android.di.data.network.routes

import io.reactivex.Single
import org.technical.android.entity.network.response.RssFeedJsonResponse
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.*

interface GoogleFeedsRoute {

    @GET("v2/everything")
    fun getFeeds(
        @Query("q") query: String = "everything",
        @Query("from") date: String? = null,
        @Query("sortBy") sortBy: String? = "publishedAt",
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int? = null
    ): Single<Result<RssFeedJsonResponse>>

}
