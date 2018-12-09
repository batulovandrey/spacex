package com.example.butul0ve.spacex.mvp.interactor

import com.example.butul0ve.spacex.db.LaunchesRepository
import com.example.butul0ve.spacex.db.model.Launch
import com.example.butul0ve.spacex.network.api.NetworkHelper
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class MainInteractor @Inject constructor(override val networkHelper: NetworkHelper,
                     val repository: LaunchesRepository):
        BaseInteractor(networkHelper), MainMvpInteractor {

    override fun getPastFlightsFromServer(launchYear: Int): Single<List<Launch>> {
        val startEnd = if (launchYear == 0) {
            Calendar.getInstance().get(Calendar.YEAR)
        } else {
            launchYear
        }
        return networkHelper.getFlights(startEnd)
    }

    override fun getNextLaunch(): Single<Launch> {
        return networkHelper.getNext()
    }

    override fun getPastLaunchesFromDb(): Flowable<List<Launch>> {
        return repository.getAllPastLaunches()
    }

    override fun replacePastLaunches(launches: List<Launch>): Completable {
        return Completable.fromAction {
            repository.deleteAllPastLaunches()
            repository.insertLaunches(launches)
        }
    }
}