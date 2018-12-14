package com.github.butul0ve.spacexchecker.mvp.interactor

import com.github.butul0ve.spacexchecker.db.model.Launch
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface MainMvpInteractor: MvpInteractor {

    fun getPastFlightsFromServer(launchYear: Int = 0): Single<List<Launch>>

    fun getNextLaunch(): Single<Launch>

    fun getPastLaunchesFromDb(): Flowable<List<Launch>>

    fun replacePastLaunches(launches: List<Launch>): Completable
}