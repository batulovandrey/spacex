package com.example.butul0ve.spacex.api

import com.example.butul0ve.spacex.bean.Flight
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ServerApi {

    @GET
    fun getLaunchesByYear(@Url url: String): Call<List<Flight>>
}