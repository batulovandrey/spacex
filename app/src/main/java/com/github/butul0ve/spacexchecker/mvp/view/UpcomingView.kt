package com.github.butul0ve.spacexchecker.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.github.butul0ve.spacexchecker.adapter.LaunchesAdapter
import com.github.butul0ve.spacexchecker.adapter.LaunchesClickListener

interface UpcomingView: MvpView, LaunchesClickListener {

    fun setAdapter(adapter: LaunchesAdapter)

    fun showProgressBar()

    fun hideProgressBar()

    fun showTryAgainButton()

    fun hideTryAgainButton()

    @StateStrategyType(SkipStrategy::class)
    override fun onYoutubeButtonClick(position: Int)

    @StateStrategyType(SkipStrategy::class)
    fun openYoutube(link: String)
}