package org.technical.android.di.data.network.interceptor

import android.content.Context
import okhttp3.Interceptor
import java.io.IOException


class HeaderInterceptor constructor(
    val context: Context
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val original = chain.request()
        val builder = original.newBuilder()
        builder.addHeader("Content-Type", "application/json")
        val request = builder.build()
        return chain.proceed(request)
    }

}