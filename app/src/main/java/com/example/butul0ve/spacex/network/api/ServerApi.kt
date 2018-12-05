package com.example.butul0ve.spacex.network.api

import com.example.butul0ve.spacex.db.model.Dragon
import com.example.butul0ve.spacex.db.model.Launch
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerApi {

    @GET("launches/past")
    fun getLaunchesByYear(@Query(value = "launch_year") year: Int): Single<List<Launch>>

    @GET("launches/upcoming")
    fun getUpcomingLaunches(): Single<List<Launch>>

    @GET("dragons")
    fun getDragons(): Single<List<Dragon>>

    @GET("launches/next")
    fun getNextLaunch(): Single<Launch>
}