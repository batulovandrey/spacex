package com.example.butul0ve.spacex.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.butul0ve.spacex.adapter.LaunchesAdapter
import com.example.butul0ve.spacex.adapter.LaunchesClickListener

interface UpcomingView: MvpView, LaunchesClickListener {

    fun setAdapter(adapter: LaunchesAdapter)

    fun showProgressBar()

    fun hideProgressBar()

    @StateStrategyType(SkipStrategy::class)
    override fun onItemClick(position: Int)
}