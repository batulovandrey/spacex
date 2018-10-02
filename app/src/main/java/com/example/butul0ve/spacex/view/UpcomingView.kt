package com.example.butul0ve.spacex.view

import com.example.butul0ve.spacex.adapter.FlightAdapter

interface UpcomingView {

    fun setAdapter(adapter: FlightAdapter)

    fun showProgressBar()

    fun hideProgressBar()
}