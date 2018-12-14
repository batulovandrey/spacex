package com.github.butul0ve.spacexchecker.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.github.butul0ve.spacexchecker.adapter.RocketAdapter
import com.github.butul0ve.spacexchecker.db.model.Rocket
import com.github.butul0ve.spacexchecker.mvp.interactor.RocketsMvpInteractor
import com.github.butul0ve.spacexchecker.mvp.view.RocketsView
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
        getData()
    }

    fun getData() {
        if (viewState == null) return

        disposable.add(interactor.getRocketsFromDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    rocketAdapter = RocketAdapter(it)
                    viewState.setAdapter(rocketAdapter)
                })

        viewState.showProgressBar()

        interactor.getRocketsFromServer()
                .subscribeOn(Schedulers.io())
                .map {
                    interactor.replaceRockets(it)
                            .subscribe()
                    it
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver())
    }

    private fun getObserver(): SingleObserver<List<Rocket>> {
        return object : SingleObserver<List<Rocket>> {

            override fun onSuccess(t: List<Rocket>) {
                if (viewState == null) return

                rocketAdapter = RocketAdapter(t)
                viewState.setAdapter(rocketAdapter)
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