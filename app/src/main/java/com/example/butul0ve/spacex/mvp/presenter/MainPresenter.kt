package com.example.butul0ve.spacex.mvp.presenter

import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.example.butul0ve.spacex.adapter.LaunchesAdapter
import com.example.butul0ve.spacex.db.model.Launch
import com.example.butul0ve.spacex.mvp.interactor.MainMvpInteractor
import com.example.butul0ve.spacex.mvp.view.MainView
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(override val interactor: MainMvpInteractor) :
        BasePresenter<MainView>(interactor) {

    private lateinit var adapter: LaunchesAdapter

    fun onItemClick(position: Int) {
        if (viewState != null) {
            if (::adapter.isInitialized) {
                val launch = adapter.getLaunchById(position)
                val videoLink = Uri.parse(launch.links.videoLink)
                viewState.openYouTube(videoLink)
            }
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getNextLaunch()
        getDataFromDb()
//        getData()
    }

    fun getData(isConnected: Boolean = true) {
        if (viewState != null) {
            interactor.getPastFlightsFromServer()
                    .subscribeOn(Schedulers.io())
                    .map {
                        interactor.replacePastLaunches(it)
                                .subscribe()
                        it
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getObserver())
        } else {
            Timber.d("view is not attached")
        }
    }

    private fun getDataFromDb() {
        disposable.add(interactor.getPastLaunchesFromDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapter = LaunchesAdapter(it, viewState)
                    viewState.setAdapter(adapter)
//                    viewState.showProgressBar()
                    getData()
                })
    }

    private fun getObserver(): SingleObserver<List<Launch>> {
        return object : SingleObserver<List<Launch>> {

            override fun onSuccess(t: List<Launch>) {

                if (viewState == null) return

                if (::adapter.isInitialized) {
                    adapter.updateLaunches(t)
                } else {
                    adapter = LaunchesAdapter(t, viewState)
                }
                viewState.setAdapter(adapter)
                viewState.hideProgressBar()
            }

            override fun onSubscribe(d: Disposable) {
                disposable.add(d)
            }

            override fun onError(e: Throwable) {
                Timber.d(e)

                if (viewState == null) return
                viewState.hideProgressBar()
                viewState.showButtonTryAgain()
            }
        }
    }

    private fun getNextLaunch() {
        if (viewState != null) {
            disposable.add(interactor.getNextLaunch()
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
}