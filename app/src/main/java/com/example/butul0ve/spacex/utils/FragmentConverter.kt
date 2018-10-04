package com.example.butul0ve.spacex.utils

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.butul0ve.spacex.MainFragment
import com.example.butul0ve.spacex.DragonsFragment
import com.example.butul0ve.spacex.UpcomingFragment
import com.example.butul0ve.spacex.db.DataManager
import com.example.butul0ve.spacex.presenter.MainPresenterImpl
import com.example.butul0ve.spacex.presenter.DragonsPresenterImpl
import com.example.butul0ve.spacex.presenter.UpcomingPresenterImpl

fun String.convert(context: Context): Fragment {
    return when(this) {
        DRAGONS -> {
            val fragment = DragonsFragment()
            fragment.setPresenter(DragonsPresenterImpl())
            fragment
        }
        MAIN -> {
            val fragment = MainFragment()
            fragment.setPresenter(MainPresenterImpl(DataManager(context)))
            fragment
        }
        UPCOMING -> {
            val fragment = UpcomingFragment()
            fragment.setPresenter(UpcomingPresenterImpl())
            fragment
        }
        else  -> {
            val fragment = MainFragment()
            fragment.setPresenter(MainPresenterImpl(DataManager(context)))
            fragment
        }
    }
}