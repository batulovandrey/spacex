package com.example.butul0ve.spacex.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.butul0ve.spacex.ui.BaseFragment

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainActivityView: MvpView {

    fun showFragment(fragment: BaseFragment)
}