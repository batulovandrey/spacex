package com.github.butul0ve.spacexchecker.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.github.butul0ve.spacexchecker.adapter.RocketAdapter
import com.github.butul0ve.spacexchecker.db.model.Rocket
import com.github.butul0ve.spacexchecker.mvp.interactor.RocketsMvpInteractor
import com.github.butul0ve.spacexchecker.mvp.view.RocketsView
import io.reactivex.MaybeObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class RocketsPresenter @Inject constructor(override val interactor: RocketsMvpInteractor) :
        BasePresenter<RocketsView>(interactor) {

    private lateinit var rocketAdapter: RocketAdapter

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getDataFromDb()
    }

    fun getData() {
        if (viewState == null) return

        interactor.getRocketsFromServer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver())
    }

    private fun getDataFromDb() {
        interactor.getRocketsFromDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getDbObserver())
    }

    private fun getDbObserver(): MaybeObserver<List<Rocket>> {
        return object : MaybeObserver<List<Rocket>> {

            override fun onSuccess(t: List<Rocket>) {
                if (viewState == null) {
                    Timber.d("getDbObserver onSuccess view is not attached")
                } else {
                    rocketAdapter = if (t.isEmpty()) {
                        RocketAdapter(ArrayList())
                    } else {
                        RocketAdapter(t)
                    }
                    viewState.setAdapter(rocketAdapter)
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

                rocketAdapter = RocketAdapter(ArrayList())
                viewState.setAdapter(rocketAdapter)
                getData()
            }
        }
    }

    private fun getObserver(): SingleObserver<List<Rocket>> {
        return object : SingleObserver<List<Rocket>> {

            override fun onSuccess(t: List<Rocket>) {
                if (viewState == null) return

                disposable.add(interactor.replaceRockets(t)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            if (t.isNotEmpty()) {
                                rocketAdapter.updateRockets(t)
                                viewState.setAdapter(rocketAdapter)
                            }
                            viewState.hideProgressBar()
                            viewState.hideButtonTryAgain()
                        })
            }

            override fun onSubscribe(d: Disposable) {
                disposable.add(d)
            }

            override fun onError(e: Throwable) {
                Timber.d(e)
                if (viewState == null) return

                viewState.hideProgressBar()
                if (rocketAdapter.itemCount == 0) {
                    viewState.showButtonTryAgain()
                }
            }
        }
    }
}