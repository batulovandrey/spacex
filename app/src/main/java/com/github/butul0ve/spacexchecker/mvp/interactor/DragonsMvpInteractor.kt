package com.github.butul0ve.spacexchecker.mvp.interactor

import com.github.butul0ve.spacexchecker.db.model.Dragon
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface DragonsMvpInteractor : MvpInteractor {

    fun getDragonsFromServer(): Single<List<Dragon>>

    fun getDragonsFromDb(): Maybe<List<Dragon>>

    fun deleteDragons(): Completable

    fun insertDragon(dragon: Dragon): Completable

    fun insertDragons(dragons: List<Dragon>): Completable

    fun replaceDragons(dragons: List<Dragon>): Completable
}