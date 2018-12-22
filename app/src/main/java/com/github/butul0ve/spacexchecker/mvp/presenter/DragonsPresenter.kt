package com.github.butul0ve.spacexchecker.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.github.butul0ve.spacexchecker.adapter.DragonAdapter
import com.github.butul0ve.spacexchecker.db.model.Dragon
import com.github.butul0ve.spacexchecker.mvp.interactor.DragonsMvpInteractor
import com.github.butul0ve.spacexchecker.mvp.view.DragonsView
import io.reactivex.MaybeObserver
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
        getDataFromDb()
    }

    fun getData() {
        if (viewState != null) {
            interactor.getDragonsFromServer()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getObserver())
        }
    }

    private fun getDataFromDb() {
        interactor.getDragonsFromDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getDbObserver())
    }

    private fun getDbObserver(): MaybeObserver<List<Dragon>> {
        return object : MaybeObserver<List<Dragon>> {

            override fun onSuccess(t: List<Dragon>) {
                if (viewState == null) {
                    Timber.d("getDbObserver onSuccess view is not attached")
                } else {
                    dragonAdapter = if (t.isEmpty()) {
                        DragonAdapter(ArrayList())
                    } else {
                        DragonAdapter(t)
                    }
                    viewState.setAdapter(dragonAdapter)
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

                dragonAdapter = DragonAdapter(ArrayList())
                viewState.setAdapter(dragonAdapter)
                getData()
            }
        }
    }

    private fun getObserver(): SingleObserver<List<Dragon>> {
        return object : SingleObserver<List<Dragon>> {

            override fun onSuccess(t: List<Dragon>) {
                if (viewState == null) return

                disposable.add(interactor.replaceDragons(t)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            if (t.isNotEmpty()) {
                                dragonAdapter.updateDragons(t)
                                viewState.setAdapter(dragonAdapter)
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
                if (dragonAdapter.itemCount == 0) {
                    viewState.showButtonTryAgain()
                }
            }
        }
    }
}