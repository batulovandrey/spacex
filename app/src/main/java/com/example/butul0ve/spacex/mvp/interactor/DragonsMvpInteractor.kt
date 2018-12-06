package com.example.butul0ve.spacex.mvp.interactor

import com.example.butul0ve.spacex.db.model.Dragon
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface DragonsMvpInteractor : MvpInteractor {

    fun getDragonsFromServer(): Single<List<Dragon>>

    fun getDragonsFromDb(): Flowable<List<Dragon>>

    fun deleteDragons(): Completable

    fun insertDragon(dragon: Dragon): Completable

    fun insertDragons(dragons: List<Dragon>): Completable

    fun replaceDragons(dragons: List<Dragon>): Completable
}