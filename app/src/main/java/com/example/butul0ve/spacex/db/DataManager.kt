package com.example.butul0ve.spacex.db

import android.content.Context
import com.example.butul0ve.spacex.bean.Dragon
import com.example.butul0ve.spacex.bean.PastLaunch
import com.example.butul0ve.spacex.bean.UpcomingLaunch
import io.reactivex.Completable
import io.reactivex.Flowable

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

    fun deleteAllDragons(): Completable {
        return Completable.fromAction { dbInstance.dragonDao().deleteAll() }
    }

    fun deleteAllPastLaunches(): Completable {
        return Completable.fromAction { dbInstance.pastLaunchesDao().deleteAll() }
    }

    fun deleteAllUpcomingLaunches(): Completable {
        return Completable.fromAction { dbInstance.upcomingLaunchesDao().deleteAll() }
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