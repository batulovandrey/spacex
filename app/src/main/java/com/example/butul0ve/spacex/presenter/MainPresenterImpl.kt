package com.example.butul0ve.spacex.presenter

import android.net.Uri
import android.util.Log
import com.example.butul0ve.spacex.adapter.PastLaunchesAdapter
import com.example.butul0ve.spacex.adapter.PastLaunchesClickListener
import com.example.butul0ve.spacex.api.NetworkHelper
import com.example.butul0ve.spacex.bean.PastLaunch
import com.example.butul0ve.spacex.db.DataManager
import com.example.butul0ve.spacex.view.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by butul0ve on 21.01.18.
 */

private val TAG = MainPresenterImpl::class.java.simpleName

class MainPresenterImpl(private val dataManager: DataManager) : MainPresenter, PastLaunchesClickListener {

    private var pastLaunches: List<PastLaunch>
    private lateinit var adapter: PastLaunchesAdapter
    private lateinit var mainView: MainView
    private val networkHelper = NetworkHelper()
    private val disposable = CompositeDisposable()

    init {
        pastLaunches = dataManager.getAllPastLaunches().
                subscribeOn(Schedulers.io())
                .blockingFirst()
    }

    override fun onItemClick(position: Int) {
        val flight = pastLaunches[position]
        val videoLink = Uri.parse(flight.links.videoLink)
        openYouTube(videoLink)
    }

    override fun attachView(view: MainView) {
        mainView = view
    }

    override fun clearResources() {
        disposable.clear()
    }

    override fun showProgressBar() {
        if (isViewAttached())
            mainView.showProgressBar()
    }

    override fun hideProgressBar() {
        if (isViewAttached())
            mainView.hideProgressBar()
    }

    override fun openYouTube(uri: Uri) {
        if (isViewAttached())
            mainView.openYouTube(uri)
    }

    override fun getData() {
        if (isViewAttached()) {
            mainView.showProgressBar()

            disposable.add(networkHelper.getFlights()
                    .subscribeOn(Schedulers.io())
                    .map {
                        dataManager.deleteAllPastLaunches().subscribe()

                        dataManager.insertPastLaunches(it.reversed()).subscribe()
                        it.reversed()
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        pastLaunches = it
                        adapter = PastLaunchesAdapter(pastLaunches, this@MainPresenterImpl)
                        mainView.setAdapter(adapter)
                        mainView.hideProgressBar()
                        Log.d(TAG, "pastLaunches size is ${pastLaunches.size}")
                    },
                            {
                                Log.e(TAG, it.message)
                                mainView.hideProgressBar()
                                mainView.showButtonTryAgain()
                            }))
        }
    }

    override fun setAdapter(adapter: PastLaunchesAdapter) {
        if (isViewAttached())
            mainView.setAdapter(adapter)
    }

    override fun showToast(message: Int) {
        if (isViewAttached())
            mainView.showToast(message)
    }

    override fun showButtonTryAgain() {
        if (isViewAttached())
            mainView.showButtonTryAgain()
    }

    override fun hideButtonTryAgain() {
        if (isViewAttached())
            mainView.hideButtonTryAgain()
    }

    private fun isViewAttached(): Boolean {
        return ::mainView.isInitialized
    }

    override fun isDataLoaded(): Boolean {
        return pastLaunches.isNotEmpty()
    }

    override fun showData() {
        disposable.add(dataManager.getAllPastLaunches()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    pastLaunches = it
                    adapter = PastLaunchesAdapter(pastLaunches, this@MainPresenterImpl)
                    mainView.setAdapter(adapter)
                    Log.d(TAG, "pastLaunches size is ${pastLaunches.size}")
                })
    }
}