package com.example.butul0ve.spacex.utils

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.butul0ve.spacex.MainFragment
import com.example.butul0ve.spacex.DragonsFragment
import com.example.butul0ve.spacex.SpaceXApp
import com.example.butul0ve.spacex.UpcomingFragment
import com.example.butul0ve.spacex.db.DataManager
import com.example.butul0ve.spacex.presenter.MainPresenterImpl
import com.example.butul0ve.spacex.presenter.DragonsPresenterImpl
import com.example.butul0ve.spacex.presenter.UpcomingPresenterImpl

fun String.convert(context: Context): Fragment {
    return when(this) {
        DRAGONS -> {
            val fragment = DragonsFragment()
            if (SpaceXApp.dataManager == null) {
                SpaceXApp.dataManager = DataManager(context)
            }
            fragment.setPresenter(DragonsPresenterImpl(SpaceXApp.dataManager!!))
            fragment
        }
        MAIN -> {
            val fragment = MainFragment()
            if (SpaceXApp.dataManager == null) {
                SpaceXApp.dataManager = DataManager(context)
            }
            fragment.setPresenter(MainPresenterImpl(SpaceXApp.dataManager!!))
            fragment
        }
        UPCOMING -> {
            val fragment = UpcomingFragment()
            if (SpaceXApp.dataManager == null) {
                SpaceXApp.dataManager = DataManager(context)
            }
            fragment.setPresenter(UpcomingPresenterImpl(SpaceXApp.dataManager!!))
            fragment
        }
        else  -> {
            val fragment = MainFragment()
            if (SpaceXApp.dataManager == null) {
                SpaceXApp.dataManager = DataManager(context)
            }
            fragment.setPresenter(MainPresenterImpl(SpaceXApp.dataManager!!))
            fragment
        }
    }
}