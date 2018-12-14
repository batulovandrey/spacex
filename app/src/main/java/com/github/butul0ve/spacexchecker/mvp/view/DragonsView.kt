package com.github.butul0ve.spacexchecker.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.github.butul0ve.spacexchecker.adapter.DragonAdapter

@StateStrategyType(AddToEndSingleStrategy::class)
interface DragonsView: MvpView {

    fun setAdapter(adapter: DragonAdapter)

    fun showProgressBar()

    fun hideProgressBar()

    fun showButtonTryAgain()

    fun hideButtonTryAgain()
}