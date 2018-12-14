package com.github.butul0ve.spacexchecker.db

import com.github.butul0ve.spacexchecker.db.dao.LaunchesDao
import com.github.butul0ve.spacexchecker.db.model.Launch
import io.reactivex.Completable
import javax.inject.Inject

class LaunchesRepository @Inject constructor(val launchesDao: LaunchesDao) {

    fun getAllLaunches() = launchesDao.getAll()

    fun getAllUpcomingLaunches() = launchesDao.getAllUpcomingLaunches()

    fun getAllPastLaunches() = launchesDao.getAllPastLaunches()

    fun deleteAllLaunches(): Completable {
        return Completable.fromAction { launchesDao.deleteAll() }
    }

    fun deleteAllUpcomingLaunches(): Completable {
        return Completable.fromAction { launchesDao.deleteAllUpcomingLaunches() }
    }

    fun deleteAllPastLaunches(): Completable {
        return Completable.fromAction { launchesDao.deleteAllPastLaunches() }
    }

    fun insertLaunch(launch: Launch): Completable {
        return Completable.fromAction { launchesDao.insert(launch) }
    }

    fun insertLaunches(launches: List<Launch>): Completable {
        return Completable.fromAction { launchesDao.insert(launches) }
    }
}