package com.example.butul0ve.spacex.db

import com.example.butul0ve.spacex.network.api.NetworkHelper
import com.example.butul0ve.spacex.db.model.Dragon
import com.example.butul0ve.spacex.db.model.Launch
import io.reactivex.*
import timber.log.Timber
import javax.inject.Inject

class DataManager @Inject constructor(val networkHelper: NetworkHelper,
                                      val spaceXDatabase: SpaceXDataBase) {

    private lateinit var launches: List<Launch>

    fun getAllPastLaunches(isConnected: Boolean): Observable<List<Launch>> {
        val cache = Observable.fromArray(if (::launches.isInitialized) {
            launches
        } else {
            listOf()
        })
        Timber.d("get all past launches")
        return if (isConnected) {
            Observable.concat(cache, fetchAllPastLaunches())
        }
        else {
            cache
        }
    }

    private fun fetchAllPastLaunches(): Observable<List<Launch>> {
        return networkHelper.getFlights()
                .doOnSuccess {
                    Timber.d("fetch data ${it.size} size")
                    cacheInMemory(it) }.toObservable()
    }

    private fun cacheInMemory(list: List<Launch>) {
        Timber.d("cache in memory")
        launches = list
    }

    fun deleteAllDragons(): Completable {
        return Completable.fromAction { spaceXDatabase.dragonDao().deleteAll() }
    }

    fun deleteAllUpcomingLaunches(): Completable {
        return Completable.fromAction { spaceXDatabase.launchesDao().deleteAllUpcomingLaunches() }
    }

    fun insertDragons(dragons: List<Dragon>): Completable {
        return Completable.fromAction { spaceXDatabase.dragonDao().insert(dragons) }
    }

    fun insertLaunches(launches: List<Launch>): Completable {
        return Completable.fromAction { spaceXDatabase.launchesDao().insert(launches) }
    }

    fun getNextLaunch(): Single<Launch> {
        return networkHelper.getNext()
    }
}