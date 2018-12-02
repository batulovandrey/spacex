package com.example.butul0ve.spacex.mvp.view

import com.arellomobile.mvp.MvpView
import com.example.butul0ve.spacex.adapter.UpcomingLaunchesAdaper

interface UpcomingView: MvpView {

    fun setAdapter(adapter: UpcomingLaunchesAdaper)

    fun showProgressBar()

    fun hideProgressBar()
}