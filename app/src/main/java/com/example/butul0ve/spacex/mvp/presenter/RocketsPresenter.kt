package com.example.butul0ve.spacex.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.example.butul0ve.spacex.adapter.DragonAdapter
import com.example.butul0ve.spacex.db.DataManager
import com.example.butul0ve.spacex.mvp.view.RocketsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class RocketsPresenter @Inject constructor(override val dataManager: DataManager) :
        BasePresenter<RocketsView>(dataManager) {

    private lateinit var dragonAdapter: DragonAdapter

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getData()
    }

    fun getData() {
        if (viewState != null) {
            viewState.showProgressBar()
            disposable.add(dataManager.networkHelper.getDragons()
                    .subscribeOn(Schedulers.io())
                    .map {
                        dataManager.deleteAllDragons().subscribe()
                        dataManager.insertDragons(it)
                        it
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        dragonAdapter = DragonAdapter(it)
                        viewState.setAdapter(dragonAdapter)
                        viewState.hideProgressBar()
                    },
                            {
                                Timber.e(it)
                                viewState.hideProgressBar()
                            }))
        }
    }
}