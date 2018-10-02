package com.example.butul0ve.spacex.api

import com.example.butul0ve.spacex.bean.Dragon
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


    private val serverApi = retrofit.create(ServerApi::class.java)

    fun getFlights(launchYear: Int = 0): Call<List<Flight>> {
        val year = if (launchYear == 0) {
            Calendar.getInstance().get(Calendar.YEAR)
        } else {
            launchYear
        }

        return serverApi.getLaunchesByYear(year)
    }

    fun getUpcomingLaunches(launchYear: Int = 0): Call<List<Flight>> {
        val year = if (launchYear == 0) {
            Calendar.getInstance().get(Calendar.YEAR)
        } else {
            launchYear
        }

        return serverApi.getUpcomingLaunches(year)
    }

    fun getDragons(): Call<List<Dragon>> {
        return serverApi.getDragons()
    }

    companion object {

        const val JSON_URL = "https://api.spacexdata.com/v3/"
    }
}