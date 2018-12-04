package com.example.butul0ve.spacex.mvp.presenter

import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.example.butul0ve.spacex.adapter.LaunchesAdapter
import com.example.butul0ve.spacex.db.DataManager
import com.example.butul0ve.spacex.mvp.view.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@InjectViewState
class MainPresenter(override val dataManager: DataManager) :
        BasePresenter<MainView>(dataManager) {

    private lateinit var adapter: LaunchesAdapter

    fun onItemClick(position: Int) {
        if (viewState != null) {
            disposable.add(dataManager.getAllPastLaunches(true)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        val flight = it[position]
                        val videoLink = Uri.parse(flight.links.videoLink)
                        openYouTube(videoLink)
                    })
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getNextLaunch()
        getData()
    }

    fun getData(isConnected: Boolean = true) {
        if (viewState != null) {
            viewState.showProgressBar()
            disposable.add(dataManager.getAllPastLaunches(isConnected)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        adapter = LaunchesAdapter(it.reversed(), viewState)
                        viewState.setAdapter(adapter)
                        viewState.hideProgressBar()
                    },
                            {
                                viewState.hideProgressBar()
                                viewState.showButtonTryAgain()
                            }))
        } else {
            Timber.d("view is not attached")
        }
    }

    fun setAdapter(adapter: LaunchesAdapter) {
        viewState.setAdapter(adapter)
    }

    private fun getNextLaunch() {
        if (viewState != null) {
            disposable.add(dataManager.getNextLaunch()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({

                        val date = Date(it.launchDate.toLong() * 1000)
                        val launchDate = SimpleDateFormat("dd MMMM y, HH:mm:ss", Locale.ENGLISH)
                        val text = "the next launch is scheduled for:\n${launchDate.format(date)}"

                        viewState.showNextLaunch(text)

                    },
                            {
                                Timber.d("error getting data")
                            }))

        } else {
            Timber.d("view is not attached")
        }
    }

    private fun openYouTube(uri: Uri) {
        viewState.openYouTube(uri)
    }
}