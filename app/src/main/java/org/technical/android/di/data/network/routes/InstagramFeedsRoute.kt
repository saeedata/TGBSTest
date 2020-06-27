package org.technical.android.di.data.network.routes

import io.reactivex.Single
import org.technical.android.entity.network.response.RssFeedXmlResponse
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.*

interface InstagramFeedsRoute {

    @GET("feeds/w15w7kgucE7yPT5T.xml")
    fun getFeeds(): Single<Result<RssFeedXmlResponse>>

}
