package com.example.butul0ve.spacex.utils

import androidx.fragment.app.Fragment
import com.example.butul0ve.spacex.MainFragment
import com.example.butul0ve.spacex.DragonsFragment
import com.example.butul0ve.spacex.presenter.MainPresenterImpl
import com.example.butul0ve.spacex.presenter.DragonsPresenterImpl

fun String.convert(): Fragment {
    return when(this) {
        DRAGONS -> {
            val fragment = DragonsFragment()
            fragment.setPresenter(DragonsPresenterImpl())
            fragment
        }
        MAIN -> {
            val fragment = MainFragment()
            fragment.setPresenter(MainPresenterImpl())
            fragment
        }
        else  -> {
            val fragment = MainFragment()
            fragment.setPresenter(MainPresenterImpl())
            fragment
        }
    }
}