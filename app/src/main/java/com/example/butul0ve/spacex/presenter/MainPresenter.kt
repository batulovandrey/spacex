package com.example.butul0ve.spacex.presenter

import android.net.Uri
import com.example.butul0ve.spacex.adapter.PastLaunchesAdapter
import com.example.butul0ve.spacex.view.MainView

/**
 * Created by butul0ve on 21.01.18.
 */

interface MainPresenter {

    fun attachView(view: MainView)

    fun clearResources()

    fun showProgressBar()

    fun hideProgressBar()

    fun openYouTube(uri: Uri)

    fun getData(isConnected: Boolean)

    fun setAdapter(adapter: PastLaunchesAdapter)

    fun showToast(message: Int)

    fun showButtonTryAgain()

    fun hideButtonTryAgain()
}