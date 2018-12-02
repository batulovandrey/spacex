package com.example.butul0ve.spacex.mvp.view

import android.net.Uri
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.butul0ve.spacex.adapter.PastLaunchesAdapter
import com.example.butul0ve.spacex.adapter.PastLaunchesClickListener

@StateStrategyType(SkipStrategy::class)
interface MainView: View, PastLaunchesClickListener {

    fun showProgressBar()

    fun hideProgressBar()

    fun openYouTube(uri: Uri)

    fun setAdapter(adapter: PastLaunchesAdapter)

    fun showToast(message: Int)

    fun showButtonTryAgain()

    fun hideButtonTryAgain()

    fun showNextLaunch(text: String)
}