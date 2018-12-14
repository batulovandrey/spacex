package com.github.butul0ve.spacexchecker.mvp.view

import android.net.Uri
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.github.butul0ve.spacexchecker.adapter.LaunchesAdapter
import com.github.butul0ve.spacexchecker.adapter.LaunchesClickListener

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : View, LaunchesClickListener {

    fun showProgressBar()

    fun hideProgressBar()

    @StateStrategyType(SkipStrategy::class)
    fun openYouTube(uri: Uri)

    @StateStrategyType(SkipStrategy::class)
    override fun onItemClick(position: Int)

    fun setAdapter(adapter: LaunchesAdapter)

    fun showToast(message: Int)

    fun showButtonTryAgain()

    fun hideButtonTryAgain()

    @StateStrategyType(SkipStrategy::class)
    fun showNextLaunch(text: String)
}