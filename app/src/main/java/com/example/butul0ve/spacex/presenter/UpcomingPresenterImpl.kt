package com.example.butul0ve.spacex.presenter

import android.util.Log
import com.example.butul0ve.spacex.adapter.PastLaunchesClickListener
import com.example.butul0ve.spacex.adapter.UpcomingLaunchesAdaper
import com.example.butul0ve.spacex.bean.UpcomingLaunch
import com.example.butul0ve.spacex.db.DataManager
import com.example.butul0ve.spacex.view.UpcomingView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UpcomingPresenterImpl
@Inject constructor(private val dataManager: DataManager) : UpcomingPresenter,
        PastLaunchesClickListener {

    private val TAG = UpcomingPresenterImpl::class.java.name

    private lateinit var adapter: UpcomingLaunchesAdaper
    private lateinit var upcomingLaunches: List<UpcomingLaunch>
    private lateinit var upcomingView: UpcomingView
    private val disposable = CompositeDisposable()

    override fun attachView(view: UpcomingView) {
        upcomingView = view
    }

    override fun getData() {
        upcomingView.showProgressBar()
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
                    upcomingView.setAdapter(adapter)
                    upcomingView.hideProgressBar()
                },
                        {
                            Log.e(TAG, it.message)
                            upcomingView.hideProgressBar()
                        }))
    }

    override fun onItemClick(position: Int) {
        // ignore
    }
}