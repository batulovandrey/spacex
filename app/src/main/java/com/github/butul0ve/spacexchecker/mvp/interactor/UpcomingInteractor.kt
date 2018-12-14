package com.github.butul0ve.spacexchecker.mvp.interactor

import com.github.butul0ve.spacexchecker.db.LaunchesRepository
import com.github.butul0ve.spacexchecker.db.model.Launch
import com.github.butul0ve.spacexchecker.network.api.NetworkHelper
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class UpcomingInteractor @Inject constructor(override val networkHelper: NetworkHelper,
                                             val repository: LaunchesRepository) :
        UpcomingMvpInteractor, BaseInteractor(networkHelper) {

    override fun getUpcomingLaunchesFromServer(): Single<List<Launch>> {
        return networkHelper.getUpcomingLaunches()
    }

    override fun getUpcomingLaunchesFromDb(): Flowable<List<Launch>> {
        return repository.getAllUpcomingLaunches()
    }

    override fun replaceUpcomingLaunches(launches: List<Launch>): Completable {
        return Completable.fromAction {
            repository.deleteAllUpcomingLaunches()
            repository.insertLaunches(launches)
        }
    }
}