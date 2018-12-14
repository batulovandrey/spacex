package com.github.butul0ve.spacexchecker.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.github.butul0ve.spacexchecker.adapter.RocketAdapter

@StateStrategyType(AddToEndSingleStrategy::class)
interface RocketsView : MvpView {

    fun setAdapter(adapter: RocketAdapter)

    fun showProgressBar()

    fun hideProgressBar()

    fun showButtonTryAgain()

    fun hideButtonTryAgain()
}