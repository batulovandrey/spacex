package com.example.butul0ve.spacex.presenter

import com.example.butul0ve.spacex.adapter.DragonAdapter
import com.example.butul0ve.spacex.api.NetworkHelper
import com.example.butul0ve.spacex.db.DataManager
import com.example.butul0ve.spacex.view.RocketsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DragonsPresenterImpl(private var dataManager: DataManager) : DragonsPresenter {

    private val TAG = DragonsPresenterImpl::class.java.simpleName
    private lateinit var rocketsView: RocketsView
    private lateinit var dragonAdapter: DragonAdapter
    private val networkHelper = NetworkHelper()

    override fun attachView(view: RocketsView) {
        rocketsView = view
    }

    override fun getData() {
        CompositeDisposable().add(dataManager.getDragons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    dragonAdapter = DragonAdapter(it)
                    rocketsView.setAdapter(dragonAdapter)
                    rocketsView.hideProgressBar()
                })
    }
}