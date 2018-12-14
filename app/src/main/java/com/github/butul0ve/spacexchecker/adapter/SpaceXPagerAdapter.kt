package com.github.butul0ve.spacexchecker.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.github.butul0ve.spacexchecker.mvp.fragment.DragonsFragment
import com.github.butul0ve.spacexchecker.mvp.fragment.MainFragment
import com.github.butul0ve.spacexchecker.mvp.fragment.RocketsFragment
import com.github.butul0ve.spacexchecker.mvp.fragment.UpcomingFragment

class SpaceXPagerAdapter(manager: FragmentManager): FragmentPagerAdapter(manager) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> UpcomingFragment()
            1 -> MainFragment()
            2 -> DragonsFragment()
            else -> RocketsFragment()
        }
    }

    override fun getCount(): Int {
        return 4
    }
}