package com.github.butul0ve.spacexchecker.network.api

import com.github.butul0ve.spacexchecker.db.model.Dragon
import com.github.butul0ve.spacexchecker.db.model.Launch
import com.github.butul0ve.spacexchecker.db.model.Rocket
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import retrofit2.mock.BehaviorDelegate

class ServerApiTest(private val delegate: BehaviorDelegate<ServerApi>) : ServerApi {

    override fun getLaunchesByYear(year: Int): Single<List<Launch>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPastLaunches(): Single<List<Launch>> {
        val type = object : TypeToken<List<Launch>>() {}.type
        val text = javaClass.classLoader
                .getResourceAsStream("past_launches.json")
                .bufferedReader()
                .readText()
        val launches = Gson().fromJson<List<Launch>>(text, type)

        return delegate.returningResponse(launches).getPastLaunches()
    }

    override fun getUpcomingLaunches(): Single<List<Launch>> {
        val type = object : TypeToken<List<Launch>>() {}.type
        val text = javaClass.classLoader
                .getResourceAsStream("upcoming_launches.json")
                .bufferedReader()
                .readText()
        val launches = Gson().fromJson<List<Launch>>(text, type)

        return delegate.returningResponse(launches).getUpcomingLaunches()
    }

    override fun getDragons(): Single<List<Dragon>> {
        val type = object : TypeToken<List<Dragon>>() {}.type
        val text = javaClass.classLoader
                .getResourceAsStream("dragons.json")
                .bufferedReader()
                .readText()
        val dragons = Gson().fromJson<List<Dragon>>(text, type)

        return delegate.returningResponse(dragons).getDragons()
    }

    override fun getNextLaunch(): Single<Launch> {
        val type = object : TypeToken<Launch>() {}.type
        val text = javaClass.classLoader
                .getResourceAsStream("next_launch.json")
                .bufferedReader()
                .readText()
        val nextLaunch = Gson().fromJson<Launch>(text, type)

        return delegate.returningResponse(nextLaunch).getNextLaunch()
    }

    override fun getRockets(): Single<List<Rocket>> {
        val type = object : TypeToken<List<Rocket>>() {}.type
        val text = javaClass.classLoader
                .getResourceAsStream("rockets.json")
                .bufferedReader()
                .readText()

        val rockets = Gson().fromJson<List<Rocket>>(text, type)

        return delegate.returningResponse(rockets).getRockets()
    }
}