package com.github.butul0ve.spacexchecker.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.github.butul0ve.spacexchecker.adapter.LaunchesAdapter
import com.github.butul0ve.spacexchecker.db.model.Launch
import com.github.butul0ve.spacexchecker.mvp.interactor.MainMvpInteractor
import com.github.butul0ve.spacexchecker.mvp.view.MainView
import io.reactivex.MaybeObserver
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

    fun openYoutubePlayerActivity(position: Int) {
        if (viewState != null) {
            if (::adapter.isInitialized) {
                val launch = adapter.getLaunchById(position)
                launch.links.videoLink?.let {
                    viewState.openYouTube(it) }
            }
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getDataFromDb()
        getNextLaunch()
    }

    fun getData() {
        if (viewState != null) {
            interactor.getPastFlightsFromServer()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getObserver())
        } else {
            Timber.d("view is not attached")
        }
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

    private fun getDataFromDb() {
        interactor.getPastLaunchesFromDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getDbObserver())
    }

    private fun getDbObserver(): MaybeObserver<List<Launch>> {
        return object : MaybeObserver<List<Launch>> {

            override fun onSuccess(t: List<Launch>) {
                if (viewState == null) {
                    Timber.d("getdbObserver onSuccess view is not attached")
                } else {
                    adapter = if (t.isEmpty()) {
                        LaunchesAdapter(ArrayList(), viewState)
                    } else {
                        LaunchesAdapter(t.reversed(), viewState)
                    }
                    viewState.setAdapter(adapter)
                    Timber.d("getdbObserver onSucccess set adapter ${t.size}")
                    getData()
                }
            }

            override fun onComplete() {
                Timber.d("getDbObserver onComplete")

                if (viewState == null) {
                    Timber.d("getdbObserver onComplete view is not attached")
                    return
                }

                adapter = LaunchesAdapter(ArrayList(), viewState)
                viewState.setAdapter(adapter)

                getData()
            }

            override fun onSubscribe(d: Disposable) {
                disposable.add(d)
            }

            override fun onError(e: Throwable) {
                Timber.d("getDbObserver onError")
                Timber.e(e)

                adapter = LaunchesAdapter(ArrayList(), viewState)

                if (viewState == null) {
                    Timber.d("dbObserver onError view is not attached")
                } else {
                    viewState.setAdapter(adapter)
                }

                getData()
            }
        }
    }

    private fun getObserver(): SingleObserver<List<Launch>> {
        return object : SingleObserver<List<Launch>> {

            override fun onSuccess(t: List<Launch>) {

                disposable.add(interactor.replacePastLaunches(t)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            if (viewState == null) return@subscribe

                            if (t.isNotEmpty()) {
                                adapter.updateLaunches(t.reversed())
                                viewState.setAdapter(adapter)
                            }
                            viewState.hideProgressBar()
                            viewState.hideButtonTryAgain()
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
                Timber.d(e)

                if (viewState == null) return
                viewState.hideProgressBar()
                if (adapter.itemCount == 0) {
                    viewState.showButtonTryAgain()
                }
            }
        }
    }
}