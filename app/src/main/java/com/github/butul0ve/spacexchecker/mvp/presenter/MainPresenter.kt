package com.github.butul0ve.spacexchecker.mvp.presenter

import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.github.butul0ve.spacexchecker.adapter.LaunchesAdapter
import com.github.butul0ve.spacexchecker.db.model.Launch
import com.github.butul0ve.spacexchecker.mvp.interactor.MainMvpInteractor
import com.github.butul0ve.spacexchecker.mvp.view.MainView
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
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

    fun getNextLaunch() {
        if (viewState != null) {
            disposable.add(interactor.getNextLaunch()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({

                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
                        val localDateTime = LocalDateTime.parse(it.launchDate, formatter)

                        val nextLaunchDateTime = localDateTime.atZone(ZoneId.of("UTC"))
                                .withZoneSameInstant(ZoneId.systemDefault())

                        val text = "the next launch is scheduled for:\n${nextLaunchDateTime.toLocalDateTime()
                                .format(DateTimeFormatter.ofPattern("dd MMMM y, HH:mm:ss"))}"

                        viewState.showNextLaunch(text)

                    },
                            {
                                Timber.d("error getting data")
                            }))

        } else {
            Timber.d("view is not attached")
        }
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
                viewState.hideButtonTryAgain()
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
}