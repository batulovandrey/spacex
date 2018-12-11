package com.example.butul0ve.spacex.mvp.interactor

import com.example.butul0ve.spacex.db.model.Rocket
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface RocketsMvpInteractor: MvpInteractor {

    fun getRocketsFromServer(): Single<List<Rocket>>

    fun getRocketsFromDb(): Flowable<List<Rocket>>

    fun deleteRockets(): Completable

    fun insertRocket(rocket: Rocket): Completable

    fun insertRockets(rockets: List<Rocket>): Completable

    fun replaceRockets(rockets: List<Rocket>): Completable
}