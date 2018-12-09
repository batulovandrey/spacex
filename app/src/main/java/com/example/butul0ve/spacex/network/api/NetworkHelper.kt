package com.example.butul0ve.spacex.network.api

import com.example.butul0ve.spacex.db.model.Dragon
import com.example.butul0ve.spacex.db.model.Launch
import io.reactivex.Single
import javax.inject.Inject

class NetworkHelper @Inject constructor(val serverApi: ServerApi) {

    fun getFlights(launchYear: Int): Single<List<Launch>> {
        return serverApi.getLaunchesByYear(launchYear)
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