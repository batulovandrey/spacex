package com.github.butul0ve.spacexchecker.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.github.butul0ve.spacexchecker.adapter.LaunchesAdapter
import com.github.butul0ve.spacexchecker.db.model.Launch
import com.github.butul0ve.spacexchecker.mvp.interactor.UpcomingMvpInteractor
import com.github.butul0ve.spacexchecker.mvp.view.UpcomingView
import io.reactivex.MaybeObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class UpcomingPresenter @Inject constructor(override val interactor: UpcomingMvpInteractor) :
        BasePresenter<UpcomingView>(interactor) {

    private lateinit var adapter: LaunchesAdapter

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getDataFromDb()
    }

    fun getData() {
        if (viewState != null) {
            interactor.getUpcomingLaunchesFromServer()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getObserver())
        }
    }

    fun openYoutubePlayerActivity(position: Int) {
        if (viewState != null) {
            if (::adapter.isInitialized) {
                val launch = adapter.getLaunchById(position)
                launch.links.videoLink?.let { viewState.openYoutube(it) }
            }
        }
    }

    private fun getDataFromDb() {
        interactor.getUpcomingLaunchesFromDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getDbObserver())
    }

    private fun getDbObserver(): MaybeObserver<List<Launch>> {
        return object : MaybeObserver<List<Launch>> {

            override fun onSuccess(t: List<Launch>) {
                if (viewState == null) {
                    Timber.d("getDbObserver onSuccess view is not attached")
                } else {
                    adapter = if (t.isEmpty()) {
                        LaunchesAdapter(ArrayList(), viewState)
                    } else {
                        LaunchesAdapter(t, viewState)
                    }
                    viewState.setAdapter(adapter)
                    Timber.d("getDbObserver onSuccess set adapter ${t.size}")
                    getData()
                }
            }

            override fun onComplete() {
                Timber.d("getDbObserver onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                disposable.add(d)
            }

            override fun onError(e: Throwable) {
                Timber.d("getDbObserver onError")
                Timber.e(e)

                if (viewState == null) {
                    Timber.d("getDbObserver onError view is not attached")
                    return
                }

                adapter = LaunchesAdapter(ArrayList(), viewState)
                viewState.setAdapter(adapter)
                getData()
            }
        }
    }

    private fun getObserver(): SingleObserver<List<Launch>> {
        return object : SingleObserver<List<Launch>> {

            override fun onSuccess(t: List<Launch>) {
                if (viewState == null) return

                disposable.add(interactor.replaceUpcomingLaunches(t)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            if (t.isNotEmpty()) {
                                adapter.updateLaunches(t)
                                viewState.setAdapter(adapter)
                            }
                            viewState.hideProgressBar()
                            viewState.hideTryAgainButton()
                        })
            }

            override fun onSubscribe(d: Disposable) {
                disposable.add(d)
                if (viewState == null) {
                    Timber.d("getObserver view is not attached")
                } else {
                    viewState.showProgressBar()
                }
            }

            override fun onError(e: Throwable) {
                Timber.e(e)
                if (viewState == null) return

                viewState.hideProgressBar()
                if (adapter.itemCount == 0) {
                    viewState.showTryAgainButton()
                }
            }
        }
    }

    fun onRedditCampaign(position: Int) {
        if (viewState != null) {
            if (::adapter.isInitialized) {
                val launch = adapter.getLaunchById(position)
                launch.links.redditCampaign?.let {
                    viewState.openReddit(it)
                }
            }
        }
    }

    fun onRedditLaunch(position: Int) {
        if (viewState != null) {
            if (::adapter.isInitialized) {
                val launch = adapter.getLaunchById(position)
                launch.links.redditLaunch?.let {
                    viewState.openReddit(it)
                }
            }
        }
    }
}