package com.example.butul0ve.spacex.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.butul0ve.spacex.mvp.fragment.DragonsFragment
import com.example.butul0ve.spacex.mvp.fragment.MainFragment
import com.example.butul0ve.spacex.mvp.fragment.UpcomingFragment
import com.example.butul0ve.spacex.ui.BaseFragment

class SpaceXPagerAdapter(manager: FragmentManager): FragmentPagerAdapter(manager) {

    private val fragments = ArrayList<BaseFragment>()

    init {
        fragments.add(UpcomingFragment())
        fragments.add(MainFragment())
        fragments.add(DragonsFragment())
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}