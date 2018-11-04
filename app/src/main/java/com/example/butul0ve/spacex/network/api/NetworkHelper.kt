package com.example.butul0ve.spacex.network.api

import com.example.butul0ve.spacex.bean.Dragon
import com.example.butul0ve.spacex.bean.PastLaunch
import com.example.butul0ve.spacex.bean.UpcomingLaunch
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class NetworkHelper @Inject constructor(val serverApi: ServerApi) {

    fun getFlights(launchYear: Int = 0): Single<List<PastLaunch>> {
        val startEnd = if (launchYear == 0) {
            Calendar.getInstance().get(Calendar.YEAR)
        } else {
            launchYear
        }

        return serverApi.getLaunchesByYear(startEnd)
    }

    fun getUpcomingLaunches(launchYear: Int = 0): Single<List<UpcomingLaunch>> {
        val year = if (launchYear == 0) {
            Calendar.getInstance().get(Calendar.YEAR)
        } else {
            launchYear
        }

        return serverApi.getUpcomingLaunches(year)
    }

    fun getDragons(): Single<List<Dragon>> {
        return serverApi.getDragons()
    }
}