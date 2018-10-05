package com.example.butul0ve.spacex.presenter

import com.example.butul0ve.spacex.adapter.PastLaunchesClickListener
import com.example.butul0ve.spacex.adapter.UpcomingLaunchesAdaper
import com.example.butul0ve.spacex.api.NetworkHelper
import com.example.butul0ve.spacex.bean.UpcomingLaunch
import com.example.butul0ve.spacex.db.DataManager
import com.example.butul0ve.spacex.view.UpcomingView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UpcomingPresenterImpl(private val dataManager: DataManager): UpcomingPresenter, PastLaunchesClickListener {

    private val TAG = UpcomingPresenterImpl::class.java.name

    private lateinit var adapter: UpcomingLaunchesAdaper
    private lateinit var upcomingLaunches: List<UpcomingLaunch>
    private lateinit var upcomingView: UpcomingView
    private val networkHelper = NetworkHelper()

    override fun attachView(view: UpcomingView) {
        upcomingView = view
    }

    override fun getData() {
        CompositeDisposable().add(dataManager.getAllUpcomingLaunches()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    upcomingLaunches = it
                    adapter = UpcomingLaunchesAdaper(upcomingLaunches)
                    upcomingView.setAdapter(adapter)
                })
    }

    override fun onItemClick(position: Int) {
        // ignore
    }
}