package com.example.butul0ve.spacex.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.butul0ve.spacex.mvp.fragment.DragonsFragment
import com.example.butul0ve.spacex.mvp.fragment.MainFragment
import com.example.butul0ve.spacex.mvp.fragment.RocketsFragment
import com.example.butul0ve.spacex.mvp.fragment.UpcomingFragment
import com.example.butul0ve.spacex.mvp.view.MainActivityView

@InjectViewState
class MainActivityPresenter: MvpPresenter<MainActivityView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.showFragment(MainFragment())
    }

    fun openDragonsFragment() {
        viewState.showFragment(DragonsFragment())
    }

    fun openUpcomingFragment() {
        viewState.showFragment(UpcomingFragment())
    }

    fun openMainFragment() {
        viewState.showFragment(MainFragment())
    }

    fun openRocketsFragment() {
        viewState.showFragment(RocketsFragment())
    }
}