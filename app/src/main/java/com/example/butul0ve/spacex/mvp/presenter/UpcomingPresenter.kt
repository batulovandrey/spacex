package com.example.butul0ve.spacex.mvp.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.butul0ve.spacex.adapter.PastLaunchesClickListener
import com.example.butul0ve.spacex.adapter.UpcomingLaunchesAdaper
import com.example.butul0ve.spacex.bean.UpcomingLaunch
import com.example.butul0ve.spacex.db.DataManager
import com.example.butul0ve.spacex.mvp.view.UpcomingView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@InjectViewState
class UpcomingPresenter(override val dataManager: DataManager) :
        BasePresenter<UpcomingView>(dataManager), PastLaunchesClickListener {

    private val TAG = UpcomingPresenter::class.java.name

    private lateinit var adapter: UpcomingLaunchesAdaper
    private lateinit var upcomingLaunches: List<UpcomingLaunch>

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
                        dataManager.insertUpcomingLaunches(it)
                        it
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        upcomingLaunches = it
                        adapter = UpcomingLaunchesAdaper(upcomingLaunches)
                        viewState.setAdapter(adapter)
                        viewState.hideProgressBar()
                    },
                            {
                                Log.e(TAG, it.message)
                                viewState.hideProgressBar()
                            }))
        }
    }

    override fun onItemClick(position: Int) {
        // ignore
    }
}