package com.example.butul0ve.spacex.mvp.interactor

import com.example.butul0ve.spacex.db.DragonsRepository
import com.example.butul0ve.spacex.db.model.Dragon
import com.example.butul0ve.spacex.network.api.NetworkHelper
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class DragonsInteractor @Inject constructor(override val networkHelper: NetworkHelper,
                        val repository: DragonsRepository) :
        DragonsMvpInteractor, BaseInteractor(networkHelper) {

    override fun getDragonsFromServer(): Single<List<Dragon>> {
        return networkHelper.getDragons()
    }

    override fun getDragonsFromDb(): Flowable<List<Dragon>> {
        return repository.getAllDragonsFromDb()
    }

    override fun deleteDragons(): Completable {
        return repository.deleteAllDragons()
    }

    override fun insertDragon(dragon: Dragon): Completable {
        return repository.insertDragon(dragon)
    }

    override fun insertDragons(dragons: List<Dragon>): Completable {
        return repository.insertDragons(dragons)
    }

    override fun replaceDragons(dragons: List<Dragon>): Completable {
        return Completable.fromAction {
            repository.deleteAllDragons()
            repository.insertDragons(dragons)
        }
    }
}