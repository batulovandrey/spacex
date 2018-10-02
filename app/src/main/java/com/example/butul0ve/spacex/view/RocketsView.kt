package com.example.butul0ve.spacex.view

import com.example.butul0ve.spacex.adapter.DragonAdapter

interface RocketsView {

    fun setAdapter(adapter: DragonAdapter)

    fun showProgressBar()

    fun hideProgressBar()
}