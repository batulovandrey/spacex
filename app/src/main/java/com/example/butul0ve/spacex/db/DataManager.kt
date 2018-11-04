package com.example.butul0ve.spacex.db

import android.util.Log
import com.example.butul0ve.spacex.network.api.NetworkHelper
import com.example.butul0ve.spacex.bean.Dragon
import com.example.butul0ve.spacex.bean.PastLaunch
import com.example.butul0ve.spacex.bean.UpcomingLaunch
import io.reactivex.*
import javax.inject.Inject

class DataManager @Inject constructor(val networkHelper: NetworkHelper,
                                      val spaceXDatabase: SpaceXDataBase) {

    private lateinit var pastLaunches: List<PastLaunch>

    fun getAllPastLaunches(isConnected: Boolean): Observable<List<PastLaunch>> {
        val cache = Observable.fromArray(if (::pastLaunches.isInitialized) {
            pastLaunches
        } else {
            listOf()
        })
        Log.d("DataManager", "get all past launches")
        return if (isConnected) {
            Observable.concat(cache, fetchAllPastLaunches())
        }
        else {
            cache
        }
    }

    private fun fetchAllPastLaunches(): Observable<List<PastLaunch>> {
        return networkHelper.getFlights()
                .doOnSuccess {
                    Log.d("DataManager", "fetch data ${it.size} size")
                    cacheInMemory(it) }.toObservable()
    }

    private fun cacheInMemory(list: List<PastLaunch>) {
        Log.d("DataManager", "cache in memory")
        pastLaunches = list
    }

    fun getDragons(): Flowable<List<Dragon>> {
        return spaceXDatabase.dragonDao().getAll()
    }

    fun getAllUpcomingLaunches(): Flowable<List<UpcomingLaunch>> {
        return spaceXDatabase.upcomingLaunchesDao().getAll()
    }

    fun deleteAllDragons(): Completable {
        return Completable.fromAction { spaceXDatabase.dragonDao().deleteAll() }
    }

    fun deleteAllPastLaunches(): Completable {
        return Completable.fromAction { spaceXDatabase.pastLaunchesDao().deleteAll() }
    }

    fun deleteAllUpcomingLaunches(): Completable {
        return Completable.fromAction { spaceXDatabase.upcomingLaunchesDao().deleteAll() }
    }

    fun insertPastLaunches(pastLaunches: List<PastLaunch>): Completable {
        return Completable.fromAction { spaceXDatabase.pastLaunchesDao().insert(pastLaunches) }
    }

    fun insertDragons(dragons: List<Dragon>): Completable {
        return Completable.fromAction { spaceXDatabase.dragonDao().insert(dragons) }
    }

    fun insertUpcomingLaunches(upcomingLaunches: List<UpcomingLaunch>): Completable {
        return Completable.fromAction { spaceXDatabase.upcomingLaunchesDao().insert(upcomingLaunches) }
    }
}