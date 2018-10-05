package com.example.butul0ve.spacex.db

import android.content.Context
import com.example.butul0ve.spacex.bean.Dragon
import com.example.butul0ve.spacex.bean.PastLaunch
import com.example.butul0ve.spacex.bean.UpcomingLaunch
import io.reactivex.Completable
import io.reactivex.Flowable
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.async

class DataManager(context: Context) {

    private val dbInstance = SpaceXDataBase.getInstance(context)!!

    fun getAllPastLaunches(): Flowable<List<PastLaunch>> {
        return dbInstance.pastLaunchesDao().getAll()
    }

    fun getDragons(): Flowable<List<Dragon>> {
        return dbInstance.dragonDao().getAll()
    }

    fun getAllUpcomingLaunches(): Flowable<List<UpcomingLaunch>> {
        return dbInstance.upcomingLaunchesDao().getAll()
    }

    suspend fun deleteAllDragons() {
        GlobalScope.async { dbInstance.dragonDao().deleteAll() }.await()
    }

    suspend fun deleteAllPastLaunches() {
        GlobalScope.async { dbInstance.pastLaunchesDao().deleteAll() }.await()
    }

    suspend fun deleteAllUpcomingLaunches() {
        GlobalScope.async { dbInstance.upcomingLaunchesDao().deleteAll() }.await()
    }

    fun insertPastLaunches(pastLaunches: List<PastLaunch>): Completable {
        return Completable.fromAction { dbInstance.pastLaunchesDao().insert(pastLaunches) }
    }

    fun insertDragons(dragons: List<Dragon>): Completable {
        return Completable.fromAction { dbInstance.dragonDao().insert(dragons) }
    }

    fun insertUpcomingLaunches(upcomingLaunches: List<UpcomingLaunch>): Completable {
        return Completable.fromAction { dbInstance.upcomingLaunchesDao().insert(upcomingLaunches) }
    }
}