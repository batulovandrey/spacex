package com.example.butul0ve.spacex.network.api

import com.example.butul0ve.spacex.db.model.Dragon
import com.example.butul0ve.spacex.db.model.Launch
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class NetworkHelper @Inject constructor(val serverApi: ServerApi) {

    fun getFlights(launchYear: Int = 0): Single<List<Launch>> {
        val startEnd = if (launchYear == 0) {
            Calendar.getInstance().get(Calendar.YEAR)
        } else {
            launchYear
        }

        return serverApi.getLaunchesByYear(startEnd)
    }

    fun getUpcomingLaunches(): Single<List<Launch>> {
        return serverApi.getUpcomingLaunches()
    }

    fun getDragons(): Single<List<Dragon>> {
        return serverApi.getDragons()
    }

    fun getNext(): Single<Launch> {
        return serverApi.getNextLaunch()
    }
}