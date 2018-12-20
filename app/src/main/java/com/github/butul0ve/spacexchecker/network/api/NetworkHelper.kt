package com.github.butul0ve.spacexchecker.network.api

import com.github.butul0ve.spacexchecker.db.model.Dragon
import com.github.butul0ve.spacexchecker.db.model.Launch
import com.github.butul0ve.spacexchecker.db.model.Rocket
import io.reactivex.Single
import javax.inject.Inject

class NetworkHelper @Inject constructor(val serverApi: ServerApi) {

    fun getFlights(): Single<List<Launch>> {
        return serverApi.getPastLaunches()
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

    fun getRockets(): Single<List<Rocket>> {
        return serverApi.getRockets()
    }
}