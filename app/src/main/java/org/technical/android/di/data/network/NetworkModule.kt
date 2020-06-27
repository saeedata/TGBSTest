package org.technical.android.di.data.network


import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.github.aurae.retrofit2.LoganSquareConverterFactory
import com.tgbs.test.BuildConfig
import com.tgbs.test.R
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.technical.android.di.data.network.interceptor.HeaderInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class NetworkModule {


    @Provides
    @Singleton
    fun provideHeaderInterceptor(
        context: Context
    ): HeaderInterceptor {
        return HeaderInterceptor(context)
    }

    @Provides
    @Singleton
    fun provideClient(headerInterceptor: HeaderInterceptor): OkHttpClient {

        val builder = OkHttpClient.Builder()
            .connectTimeout(40, TimeUnit.SECONDS)// Set connection timeout
            .readTimeout(40, TimeUnit.SECONDS)// Read timeout
            .writeTimeout(40, TimeUnit.SECONDS)// Write timeout
            .addInterceptor(headerInterceptor)
            .cache(null)

        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(StethoInterceptor())
        }

        return builder.build()

    }

    @GoogleFeedsApi
    @Provides
    @Singleton
    fun provideGoogleFeedsApi(context: Context, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.googleFeedsApiUrl))
            .client(client)
            .addConverterFactory(LoganSquareConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    @InstagramFeedsApi
    @Provides
    @Singleton
    fun provideInstagramFeedsApi(context: Context, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.instagramFeedsApiUrl))
            .client(client)
            .addConverterFactory(TikXmlConverterFactory.create( TikXml.Builder().exceptionOnUnreadXml(false).build()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class GoogleFeedsApi

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class InstagramFeedsApi


}
