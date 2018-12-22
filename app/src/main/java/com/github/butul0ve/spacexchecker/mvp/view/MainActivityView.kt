package com.github.butul0ve.spacexchecker.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.github.butul0ve.spacexchecker.mvp.FragmentInteractor
import com.github.butul0ve.spacexchecker.ui.BaseFragment

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainActivityView : MvpView, FragmentInteractor {

    fun showFragment(fragment: BaseFragment)

    @StateStrategyType(SkipStrategy::class)
    override fun showVideo(videoId: String)
}