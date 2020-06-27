package org.technical.android.di.data.network


import org.technical.android.di.data.network.routes.*
import retrofit2.Retrofit
import javax.inject.Inject


class NetworkManager @Inject constructor(
    @NetworkModule.GoogleFeedsApi private val retrofitGoogleFeedsApi: Retrofit,
    @NetworkModule.InstagramFeedsApi private val retrofitInstagramFeedsApi: Retrofit

) {

    fun getGoogleFeedsApi(): GoogleFeedsRoute {
        return retrofitGoogleFeedsApi.create(GoogleFeedsRoute::class.java)
    }

    fun getInstagramFeedsFeedApi(): InstagramFeedsRoute {
        return retrofitInstagramFeedsApi.create(InstagramFeedsRoute::class.java)
    }

}
