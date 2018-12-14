package com.github.butul0ve.spacexchecker.di

import android.content.Context
import com.github.butul0ve.spacexchecker.network.OkHttpClientWithCache
import com.github.butul0ve.spacexchecker.network.api.NetworkHelper
import com.github.butul0ve.spacexchecker.network.api.ServerApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetModule(private val url: String) {

    @Provides
    fun provideOkHttpClientWithCache(context: Context): OkHttpClient {
        return OkHttpClientWithCache.getInstance(context)
    }

    @Provides
    fun provideRetrofit(okHttpClientWithCache: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClientWithCache)
                .build()
    }

    @Provides
    fun provideServerApi(retrofit: Retrofit): ServerApi {
        return retrofit.create(ServerApi::class.java)
    }

    @Provides
    fun provideNetworkHelper(serverApi: ServerApi): NetworkHelper {
        return NetworkHelper(serverApi)
    }
}