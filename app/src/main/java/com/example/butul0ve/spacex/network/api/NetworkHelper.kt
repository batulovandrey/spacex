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

    fun getUpcomingLaunches(): Single<List<UpcomingLaunch>> {
        return serverApi.getUpcomingLaunches()
    }

    fun getDragons(): Single<List<Dragon>> {
        return serverApi.getDragons()
    }

    fun getNext(): Single<UpcomingLaunch> {
        return serverApi.getNextLaunch()
    }
}