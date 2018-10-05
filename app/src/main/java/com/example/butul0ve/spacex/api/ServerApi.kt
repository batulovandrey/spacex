package com.example.butul0ve.spacex.api

import com.example.butul0ve.spacex.bean.Dragon
import com.example.butul0ve.spacex.bean.PastLaunch
import com.example.butul0ve.spacex.bean.UpcomingLaunch
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerApi {

    @GET("launches/past")
    fun getLaunchesByYear(@Query(value = "launch_year") year: Int): Flowable<List<PastLaunch>>

    @GET("launches/upcoming")
    fun getUpcomingLaunches(@Query(value = "launch_year") year: Int): Flowable<List<UpcomingLaunch>>

    @GET("dragons")
    fun getDragons(): Flowable<List<Dragon>>
}