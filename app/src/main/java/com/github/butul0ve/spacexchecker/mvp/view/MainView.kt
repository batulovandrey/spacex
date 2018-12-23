package com.github.butul0ve.spacexchecker.mvp.view

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.github.butul0ve.spacexchecker.adapter.LaunchesAdapter
import com.github.butul0ve.spacexchecker.adapter.LaunchesClickListener

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : View, LaunchesClickListener {

    @StateStrategyType(SkipStrategy::class)
    override fun onYoutubeButtonClick(position: Int)

    @StateStrategyType(SkipStrategy::class)
    override fun onRedditCampaignButtonClick(position: Int)

    @StateStrategyType(SkipStrategy::class)
    override fun onRedditLaunchButtonClick(position: Int)

    @StateStrategyType(SkipStrategy::class)
    fun openYouTube(link: String)

    @StateStrategyType(SkipStrategy::class)
    fun showNextLaunch(text: String)

    @StateStrategyType(SkipStrategy::class)
    fun openReddit(link: String)

    fun showProgressBar()

    fun hideProgressBar()

    fun setAdapter(adapter: LaunchesAdapter)

    fun showToast(message: Int)

    fun showButtonTryAgain()

    fun hideButtonTryAgain()
}