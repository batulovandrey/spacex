package com.example.butul0ve.spacex.view

import com.example.butul0ve.spacex.adapter.UpcomingLaunchesAdaper

interface UpcomingView {

    fun setAdapter(adapter: UpcomingLaunchesAdaper)

    fun showProgressBar()

    fun hideProgressBar()
}