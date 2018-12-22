package com.github.butul0ve.spacexchecker.mvp.interactor

import com.github.butul0ve.spacexchecker.db.model.Rocket
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface RocketsMvpInteractor: MvpInteractor {

    fun getRocketsFromServer(): Single<List<Rocket>>

    fun getRocketsFromDb(): Maybe<List<Rocket>>

    fun deleteRockets(): Completable

    fun insertRocket(rocket: Rocket): Completable

    fun insertRockets(rockets: List<Rocket>): Completable

    fun replaceRockets(rockets: List<Rocket>): Completable
}