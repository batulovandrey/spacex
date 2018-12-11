package com.example.butul0ve.spacex.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.butul0ve.spacex.adapter.RocketAdapter

@StateStrategyType(AddToEndSingleStrategy::class)
interface RocketsView : MvpView {

    fun setAdapter(adapter: RocketAdapter)

    fun showProgressBar()

    fun hideProgressBar()

    fun showButtonTryAgain()

    fun hideButtonTryAgain()
}