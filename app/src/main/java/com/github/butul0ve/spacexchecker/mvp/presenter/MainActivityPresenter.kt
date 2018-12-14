package com.github.butul0ve.spacexchecker.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.butul0ve.spacexchecker.mvp.fragment.DragonsFragment
import com.github.butul0ve.spacexchecker.mvp.fragment.MainFragment
import com.github.butul0ve.spacexchecker.mvp.fragment.RocketsFragment
import com.github.butul0ve.spacexchecker.mvp.fragment.UpcomingFragment
import com.github.butul0ve.spacexchecker.mvp.view.MainActivityView

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