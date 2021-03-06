package com.github.butul0ve.spacexchecker.mvp.interactor

import com.github.butul0ve.spacexchecker.db.LaunchesRepository
import com.github.butul0ve.spacexchecker.db.model.Launch
import com.github.butul0ve.spacexchecker.network.api.NetworkHelper
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class UpcomingInteractor @Inject constructor(override val networkHelper: NetworkHelper,
                                             val repository: LaunchesRepository) :
        UpcomingMvpInteractor, BaseInteractor(networkHelper) {

    override fun getUpcomingLaunchesFromServer(): Single<List<Launch>> {
        return networkHelper.getUpcomingLaunches()
    }

    override fun getUpcomingLaunchesFromDb(): Maybe<List<Launch>> {
        return repository.getAllUpcomingLaunches()
    }

    override fun replaceUpcomingLaunches(launches: List<Launch>): Completable {
        return repository.deleteAllUpcomingLaunches()
                .andThen(repository.insertLaunches(launches))
    }
}