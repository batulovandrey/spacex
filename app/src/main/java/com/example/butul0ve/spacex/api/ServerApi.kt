package com.example.butul0ve.spacex.api

import com.example.butul0ve.spacex.bean.Dragon
import com.example.butul0ve.spacex.bean.Flight
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerApi {

    @GET("launches")
    fun getLaunchesByYear(@Query(value = "launch_year") year: Int): Call<List<Flight>>

    @GET("dragons")
    fun getDragons(): Call<List<Dragon>>
}