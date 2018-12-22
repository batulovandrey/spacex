package com.github.butul0ve.spacexchecker.mvp.interactor

import com.github.butul0ve.spacexchecker.db.RocketsRepository
import com.github.butul0ve.spacexchecker.db.model.Rocket
import com.github.butul0ve.spacexchecker.network.api.NetworkHelper
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class RocketsInteractor @Inject constructor(override val networkHelper: NetworkHelper,
                                            val repository: RocketsRepository) : BaseInteractor(networkHelper),
        RocketsMvpInteractor {

    override fun getRocketsFromServer(): Single<List<Rocket>> {
        return networkHelper.getRockets()
    }

    override fun getRocketsFromDb(): Maybe<List<Rocket>> {
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