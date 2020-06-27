package org.technical.android.util.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpStreamFetcher
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.module.AppGlideModule
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.InputStream

@GlideModule
class MyAppGlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.prepend(
            GlideUrl::class.java, InputStream::class.java,
            HeaderLoader.Factory(context)
        )
    }

    private class HeaderLoader(
        var context: Context,
        var client: Call.Factory
    ) : OkHttpUrlLoader(client) {
        override fun buildLoadData(
            model: GlideUrl,
            width: Int,
            height: Int,
            options: Options
        ): ModelLoader.LoadData<InputStream>? {
            val newBuilder =
                (client as OkHttpClient).newBuilder().addNetworkInterceptor(StethoInterceptor())

            return ModelLoader.LoadData(model, OkHttpStreamFetcher(newBuilder.build(), model))
        }


        class Factory
        /**
         * Constructor for a new Factory that runs requests using given client.
         *
         * @param client this is typically an instance of `OkHttpClient`.
         */
        @JvmOverloads constructor(
            var context: Context,
            val client: Call.Factory = getInternalClient()
        ) : ModelLoaderFactory<GlideUrl, InputStream> {

            override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<GlideUrl, InputStream> {
                return HeaderLoader(
                    context,
                    client
                )
            }

            override fun teardown() {
                // Do nothing, this instance doesn't own the client.
            }

            companion object {

                @Volatile
                private var internalClient: Call.Factory? = null

                private fun getInternalClient(): Call.Factory {
                    if (internalClient == null) {
                        synchronized(Factory::class.java) {
                            if (internalClient == null) {
                                internalClient = OkHttpClient()
                            }
                        }
                    }
                    return internalClient!!
                }
            }
        }

    }
}