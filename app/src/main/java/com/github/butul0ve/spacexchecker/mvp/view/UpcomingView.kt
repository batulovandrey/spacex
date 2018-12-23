package com.github.butul0ve.spacexchecker.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.github.butul0ve.spacexchecker.adapter.LaunchesAdapter
import com.github.butul0ve.spacexchecker.adapter.LaunchesClickListener

interface UpcomingView: MvpView, LaunchesClickListener {

    @StateStrategyType(SkipStrategy::class)
    override fun onYoutubeButtonClick(position: Int)

    @StateStrategyType(SkipStrategy::class)
    fun openYoutube(link: String)

    @StateStrategyType(SkipStrategy::class)
    override fun onRedditCampaignButtonClick(position: Int)

    @StateStrategyType(SkipStrategy::class)
    override fun onRedditLaunchButtonClick(position: Int)

    @StateStrategyType(SkipStrategy::class)
    fun openReddit(link: String)

    fun setAdapter(adapter: LaunchesAdapter)

    fun showProgressBar()

    fun hideProgressBar()

    fun showTryAgainButton()

    fun hideTryAgainButton()
}