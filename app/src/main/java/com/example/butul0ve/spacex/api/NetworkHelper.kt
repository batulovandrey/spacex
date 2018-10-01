package com.example.butul0ve.spacex.api

import com.example.butul0ve.spacex.bean.Flight
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class NetworkHelper {

    private val retrofit = Retrofit.Builder()
            .baseUrl(JSON_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getFlights(launchYear: Int = 0): Call<List<Flight>> {
        val year = if (launchYear == 0) {
            Calendar.getInstance().get(Calendar.YEAR)
        } else {
            launchYear
        }

        return retrofit.create(ServerApi::class.java).getLaunchesByYear("launches?launch_year=$year")
    }

    companion object {

        const val JSON_URL = "https://api.spacexdata.com/v2/"
    }
}