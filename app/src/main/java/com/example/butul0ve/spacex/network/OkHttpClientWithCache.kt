package com.example.butul0ve.spacex.network

import android.content.Context
import com.example.butul0ve.spacex.utils.isConnectedToInternet
import okhttp3.Cache
import okhttp3.OkHttpClient

class OkHttpClientWithCache {

    companion object {

        private var INSTANCE: OkHttpClient? = null

        fun getInstance(context: Context): OkHttpClient {

            if (INSTANCE == null) {
                synchronized(OkHttpClientWithCache::class) {
                    val cacheSize = (10 * 1024 * 1024).toLong()
                    val cache = Cache(context.cacheDir, cacheSize)
                    INSTANCE = OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor { chain ->

                                var request = chain.request()

                                request = if (context.isConnectedToInternet()) {
                                    request.newBuilder().header("Cache-Control", "public, max-age" + 10).build()
                                } else {
                                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                                }
                                chain.proceed(request)
                            }.build()
                }
            }

            return INSTANCE!!
        }
    }
}