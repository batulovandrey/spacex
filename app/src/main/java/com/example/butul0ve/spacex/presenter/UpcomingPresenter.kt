package com.example.butul0ve.spacex.presenter

import com.example.butul0ve.spacex.view.UpcomingView

interface UpcomingPresenter {

    fun attachView(view: UpcomingView)

    fun getData()
}