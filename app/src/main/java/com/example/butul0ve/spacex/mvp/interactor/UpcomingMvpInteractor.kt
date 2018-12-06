package com.example.butul0ve.spacex.mvp.interactor

import com.example.butul0ve.spacex.db.model.Launch
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface UpcomingMvpInteractor: MvpInteractor {

    fun getUpcomingLaunchesFromServer(): Single<List<Launch>>

    fun getUpcomingLaunchesFromDb(): Flowable<List<Launch>>

    fun replaceUpcomingLaunches(launches: List<Launch>): Completable
}