package com.github.butul0ve.spacexchecker.mvp.interactor

import com.github.butul0ve.spacexchecker.db.model.Launch
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface UpcomingMvpInteractor: MvpInteractor {

    fun getUpcomingLaunchesFromServer(): Single<List<Launch>>

    fun getUpcomingLaunchesFromDb(): Maybe<List<Launch>>

    fun replaceUpcomingLaunches(launches: List<Launch>): Completable
}