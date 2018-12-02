package com.example.butul0ve.spacex.utils

import com.example.butul0ve.spacex.mvp.fragment.DragonsFragment
import com.example.butul0ve.spacex.mvp.fragment.MainFragment
import com.example.butul0ve.spacex.mvp.fragment.UpcomingFragment
import com.example.butul0ve.spacex.ui.BaseFragment

fun String.convert(): BaseFragment {
    return when (this) {
        DRAGONS -> {
            DragonsFragment()
        }
        MAIN -> {
            MainFragment()
        }
        UPCOMING -> {
            UpcomingFragment()
        }
        else -> {
            MainFragment()
        }
    }
}