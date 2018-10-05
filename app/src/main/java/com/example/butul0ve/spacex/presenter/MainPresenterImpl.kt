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

    private lateinit var pastLaunches: List<PastLaunch>
    private lateinit var adapter: PastLaunchesAdapter
    private lateinit var mainView: MainView

    private val networkHelper = NetworkHelper()

    override fun onItemClick(position: Int) {
        if (::pastLaunches.isInitialized) {
            val flight = pastLaunches[position]
            val videoLink = Uri.parse(flight.links.videoLink)
            openYouTube(videoLink)
        }
    }

    override fun attachView(view: MainView) {
        mainView = view
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
            CompositeDisposable().add(dataManager.getAllPastLaunches()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        pastLaunches = it.reversed()
                        adapter = PastLaunchesAdapter(pastLaunches, this@MainPresenterImpl)
                        mainView.setAdapter(adapter)
                        Log.d(TAG, "pastLaunches size is ${pastLaunches.size}")
                    })
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
        return ::adapter.isInitialized &&
                ::pastLaunches.isInitialized &&
                pastLaunches.isNotEmpty()
    }

    override fun showData() {
        mainView.setAdapter(adapter)
    }
}