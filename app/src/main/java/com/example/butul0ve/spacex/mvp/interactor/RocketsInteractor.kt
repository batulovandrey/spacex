package com.example.butul0ve.spacex.mvp.interactor

import com.example.butul0ve.spacex.db.RocketsRepository
import com.example.butul0ve.spacex.db.model.Rocket
import com.example.butul0ve.spacex.network.api.NetworkHelper
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class RocketsInteractor @Inject constructor(override val networkHelper: NetworkHelper,
                                            val repository: RocketsRepository) : BaseInteractor(networkHelper),
        RocketsMvpInteractor {

    override fun getRocketsFromServer(): Single<List<Rocket>> {
        return networkHelper.getRockets()
    }

    override fun getRocketsFromDb(): Flowable<List<Rocket>> {
        return repository.getAllRocketsFromDb()
    }

    override fun deleteRockets(): Completable {
        return repository.deleteAllRockets()
    }

    override fun insertRocket(rocket: Rocket): Completable {
        return repository.insertRocket(rocket)
    }

    override fun insertRockets(rockets: List<Rocket>): Completable {
        return repository.insertRockets(rockets)
    }

    override fun replaceRockets(rockets: List<Rocket>): Completable {
        return Completable.fromAction {
            repository.deleteAllRockets()
            repository.insertRockets(rockets)
        }
    }
}