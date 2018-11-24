package com.example.butul0ve.spacex.network.api

import com.example.butul0ve.spacex.bean.Dragon
import com.example.butul0ve.spacex.bean.PastLaunch
import com.example.butul0ve.spacex.bean.UpcomingLaunch
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerApi {

    @GET("launches/past")
    fun getLaunchesByYear(@Query(value = "launch_year") year: Int): Single<List<PastLaunch>>

    @GET("launches/upcoming")
    fun getUpcomingLaunches(@Query(value = "launch_year") year: Int): Single<List<UpcomingLaunch>>

    @GET("dragons")
    fun getDragons(): Single<List<Dragon>>

    @GET("launches/next")
    fun getNextLaunch(): Single<UpcomingLaunch>
}