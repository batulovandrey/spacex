package com.example.butul0ve.spacex.presenter

import android.util.Log
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
    private val disposable = CompositeDisposable()

    override fun attachView(view: RocketsView) {
        rocketsView = view
    }

    override fun getData() {
        rocketsView.showProgressBar()
        disposable.add(networkHelper.getDragons()
                .subscribeOn(Schedulers.io())
                .map {
                    dataManager.deleteAllDragons().subscribe()
                    dataManager.insertDragons(it)
                    it
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dragonAdapter = DragonAdapter(it)
                    rocketsView.setAdapter(dragonAdapter)
                    rocketsView.hideProgressBar()
                },
                        {
                            Log.e(TAG, it.message)
                            rocketsView.hideProgressBar()
                        }))
    }
}