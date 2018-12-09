package com.example.butul0ve.spacex.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.butul0ve.spacex.mvp.fragment.MainFragment
import com.example.butul0ve.spacex.ui.BaseFragment

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainActivityView : MvpView, MainFragment.OnItemClickListener {

    fun showFragment(fragment: BaseFragment)

    @StateStrategyType(SkipStrategy::class)
    override fun onItemClick(videoId: String)
}