package com.example.butul0ve.spacex.presenter

import android.net.Uri
import com.example.butul0ve.spacex.adapter.FlightAdapter
import com.example.butul0ve.spacex.model.MainModel
import com.example.butul0ve.spacex.view.MainView

/**
 * Created by butul0ve on 21.01.18.
 */

class MainPresenterImpl(private val mMainView: MainView) : MainPresenter {

    private val mMainModel = MainModel(this)

    override fun showProgressBar() {
        mMainView.showProgressBar()
    }

    override fun hideProgressBar() {
        mMainView.hideProgressBar()
    }

    override fun openYouTube(uri: Uri) {
        mMainView.openYouTube(uri)
    }

    override fun getData() {
        mMainModel.getData()
    }

    override fun setAdapter(adapter: FlightAdapter) {
        mMainView.setAdapter(adapter)
    }

    override fun showToast(message: Int) {
        mMainView.showToast(message)
    }

    override fun showButtonTryAgain() {
        mMainView.showButtonTryAgain()
    }

    override fun hideButtonTryAgain() {
        mMainView.hideButtonTryAgain()
    }
}