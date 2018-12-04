package com.example.butul0ve.spacex.mvp.view

import android.net.Uri
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.butul0ve.spacex.adapter.PastLaunchesAdapter
import com.example.butul0ve.spacex.adapter.PastLaunchesClickListener

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : View, PastLaunchesClickListener {

    fun showProgressBar()

    fun hideProgressBar()

    @StateStrategyType(SkipStrategy::class)
    fun openYouTube(uri: Uri)

    @StateStrategyType(SkipStrategy::class)
    override fun onItemClick(position: Int)

    fun setAdapter(adapter: PastLaunchesAdapter)

    fun showToast(message: Int)

    fun showButtonTryAgain()

    fun hideButtonTryAgain()

    @StateStrategyType(SkipStrategy::class)
    fun showNextLaunch(text: String)
}