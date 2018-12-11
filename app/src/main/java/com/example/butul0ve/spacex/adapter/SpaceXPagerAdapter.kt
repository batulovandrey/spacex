package com.example.butul0ve.spacex.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.butul0ve.spacex.mvp.fragment.DragonsFragment
import com.example.butul0ve.spacex.mvp.fragment.MainFragment
import com.example.butul0ve.spacex.mvp.fragment.UpcomingFragment

class SpaceXPagerAdapter(manager: FragmentManager): FragmentPagerAdapter(manager) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> UpcomingFragment()
            1 -> MainFragment()
            else -> DragonsFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }
}