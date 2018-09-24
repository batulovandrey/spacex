package com.example.butul0ve.spacex.api

import com.example.butul0ve.spacex.bean.Flight
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkHelper {

    private val retrofit = Retrofit.Builder()
            .baseUrl(JSON_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getFlights(): Call<List<Flight>> {
        return retrofit.create(ServerApi::class.java).getLaunchesByYear("launches?launch_year=2018")
    }

    companion object {

        const val JSON_URL = "https://api.spacexdata.com/v2/"
    }
}