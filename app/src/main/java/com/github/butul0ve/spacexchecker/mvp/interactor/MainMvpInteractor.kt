package com.github.butul0ve.spacexchecker.mvp.interactor

import com.github.butul0ve.spacexchecker.db.model.Launch
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface MainMvpInteractor: MvpInteractor {

    fun getPastFlightsFromServer(): Single<List<Launch>>

    fun getNextLaunch(): Single<Launch>

    fun getPastLaunchesFromDb(): Maybe<List<Launch>>

    fun replacePastLaunches(launches: List<Launch>): Completable
}