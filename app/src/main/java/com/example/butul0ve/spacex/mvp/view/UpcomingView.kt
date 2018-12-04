package com.example.butul0ve.spacex.mvp.view

import com.arellomobile.mvp.MvpView
import com.example.butul0ve.spacex.adapter.LaunchesAdapter
import com.example.butul0ve.spacex.adapter.LaunchesClickListener

interface UpcomingView: MvpView, LaunchesClickListener {

    fun setAdapter(adapter: LaunchesAdapter)

    fun showProgressBar()

    fun hideProgressBar()
}