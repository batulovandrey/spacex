package com.example.butul0ve.spacex.api

import com.example.butul0ve.spacex.bean.Dragon
import com.example.butul0ve.spacex.bean.PastLaunch
import com.example.butul0ve.spacex.bean.UpcomingLaunch
import io.reactivex.Flowable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class NetworkHelper {

    private val retrofit = Retrofit.Builder()
            .baseUrl(JSON_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()


    private val serverApi = retrofit.create(ServerApi::class.java)

    fun getFlights(launchYear: Int = 0): Flowable<List<PastLaunch>> {
        val startEnd = if (launchYear == 0) {
            Calendar.getInstance().get(Calendar.YEAR)
        } else {
            launchYear
        }

        return serverApi.getLaunchesByYear(startEnd)
    }

    fun getUpcomingLaunches(launchYear: Int = 0): Flowable<List<UpcomingLaunch>> {
        val year = if (launchYear == 0) {
            Calendar.getInstance().get(Calendar.YEAR)
        } else {
            launchYear
        }

        return serverApi.getUpcomingLaunches(year)
    }

    fun getDragons(): Flowable<List<Dragon>> {
        return serverApi.getDragons()
    }

    companion object {

        const val JSON_URL = "https://api.spacexdata.com/v3/"
    }
}