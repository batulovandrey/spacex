package com.github.butul0ve.spacexchecker.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.github.butul0ve.spacexchecker.adapter.DragonAdapter
import com.github.butul0ve.spacexchecker.db.model.Dragon
import com.github.butul0ve.spacexchecker.mvp.interactor.DragonsMvpInteractor
import com.github.butul0ve.spacexchecker.mvp.view.DragonsView
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class DragonsPresenter @Inject constructor(override val interactor: DragonsMvpInteractor) :
        BasePresenter<DragonsView>(interactor) {

    private lateinit var dragonAdapter: DragonAdapter

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getData()
    }

    fun getData() {
        if (viewState != null) {

            disposable.add(interactor.getDragonsFromDb()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        dragonAdapter = DragonAdapter(it)
                        viewState.setAdapter(dragonAdapter)
                    })

            viewState.showProgressBar()

            interactor.getDragonsFromServer()
                    .subscribeOn(Schedulers.io())
                    .map {
                        interactor.replaceDragons(it)
                                .subscribe()
                        it
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getObserver())
        }
    }

    private fun getObserver(): SingleObserver<List<Dragon>> {
        return object : SingleObserver<List<Dragon>> {

            override fun onSuccess(t: List<Dragon>) {
                if (viewState == null) return

                dragonAdapter = DragonAdapter(t)
                viewState.setAdapter(dragonAdapter)
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