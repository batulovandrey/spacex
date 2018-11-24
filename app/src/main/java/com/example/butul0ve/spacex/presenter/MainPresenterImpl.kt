package com.example.butul0ve.spacex.presenter

import android.net.Uri
import android.util.Log
import com.example.butul0ve.spacex.adapter.PastLaunchesAdapter
import com.example.butul0ve.spacex.adapter.PastLaunchesClickListener
import com.example.butul0ve.spacex.db.DataManager
import com.example.butul0ve.spacex.view.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by butul0ve on 21.01.18.
 */

private val TAG = MainPresenterImpl::class.java.simpleName

class MainPresenterImpl
@Inject constructor(private val dataManager: DataManager) : MainPresenter,
        PastLaunchesClickListener {

    private lateinit var adapter: PastLaunchesAdapter
    private lateinit var mainView: MainView
    private val disposable = CompositeDisposable()

    override fun onItemClick(position: Int) {
        disposable.add(dataManager.getAllPastLaunches(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val flight = it[position]
                    val videoLink = Uri.parse(flight.links.videoLink)
                    openYouTube(videoLink)
                })
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

    override fun getData(isConnected: Boolean) {
        if (isViewAttached()) {
            mainView.showProgressBar()
            disposable.add(dataManager.getAllPastLaunches(isConnected)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete { getNextLaunch() }
                    .subscribe({
                        adapter = PastLaunchesAdapter(it, this@MainPresenterImpl)
                        mainView.setAdapter(adapter)
                        mainView.hideProgressBar()
                        Log.d(TAG, "pastLaunches size is ${it.size}")
                    },
                            {
                                Log.e(TAG, it.message)
                                mainView.hideProgressBar()
                                mainView.showButtonTryAgain()
                            }))

        }
    }

    override fun getNextLaunch() {
        if (isViewAttached()) {
            mainView.showProgressBar()
            disposable.add(dataManager.getNextLaunch()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({

                        val date = Date(it.launchDate.toLong() * 1000)
                        val launchDate = SimpleDateFormat("dd MMMM y, HH:mm:ss", Locale.ENGLISH)
                        val text = "the next launch is scheduled for:\n${launchDate.format(date)}"

                        mainView.showNextLaunch(text)

                        mainView.hideProgressBar()
                        Log.d("mainpresenter", "get next launch ${it.details}")
                    },
                            {
                                mainView.hideProgressBar()
                                Log.d("mainpresenter", "error getting data")
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
}