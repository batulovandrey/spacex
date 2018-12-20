package com.github.butul0ve.spacexchecker.network.api

import com.github.butul0ve.spacexchecker.db.model.Dragon
import com.github.butul0ve.spacexchecker.db.model.Launch
import com.github.butul0ve.spacexchecker.db.model.Rocket
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerApi {

    @GET("launches/past")
    fun getLaunchesByYear(@Query(value = "launch_year") year: Int): Single<List<Launch>>

    @GET("launches/past")
    fun getPastLaunches(): Single<List<Launch>>

    @GET("launches/upcoming")
    fun getUpcomingLaunches(): Single<List<Launch>>

    @GET("dragons")
    fun getDragons(): Single<List<Dragon>>

    @GET("launches/next")
    fun getNextLaunch(): Single<Launch>

    @GET("rockets")
    fun getRockets(): Single<List<Rocket>>
}