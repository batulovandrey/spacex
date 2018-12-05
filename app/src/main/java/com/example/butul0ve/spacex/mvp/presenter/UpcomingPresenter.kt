package com.example.butul0ve.spacex.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.example.butul0ve.spacex.adapter.LaunchesAdapter
import com.example.butul0ve.spacex.db.model.Launch
import com.example.butul0ve.spacex.db.DataManager
import com.example.butul0ve.spacex.mvp.view.UpcomingView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

@InjectViewState
class UpcomingPresenter(override val dataManager: DataManager) :
        BasePresenter<UpcomingView>(dataManager) {

    private lateinit var adapter: LaunchesAdapter
    private lateinit var upcomingLaunches: List<Launch>

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getData()
    }

    fun getData() {
        if (viewState != null) {
            viewState.showProgressBar()
            disposable.add(dataManager.networkHelper.getUpcomingLaunches()
                    .subscribeOn(Schedulers.io())
                    .map {
                        dataManager.deleteAllUpcomingLaunches()
                        dataManager.insertLaunches(it)
                        it
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        upcomingLaunches = it
                        adapter = LaunchesAdapter(upcomingLaunches.filter {
                            launch -> !launch.details.isNullOrEmpty()
                        }, viewState)
                        viewState.setAdapter(adapter)
                        viewState.hideProgressBar()
                    },
                            {
                                Timber.e(it)
                                viewState.hideProgressBar()
                            }))
        }
    }
}