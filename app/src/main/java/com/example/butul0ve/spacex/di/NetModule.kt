package com.example.butul0ve.spacex.di

import android.content.Context
import com.example.butul0ve.spacex.network.OkHttpClientWithCache
import com.example.butul0ve.spacex.network.api.NetworkHelper
import com.example.butul0ve.spacex.network.api.ServerApi
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