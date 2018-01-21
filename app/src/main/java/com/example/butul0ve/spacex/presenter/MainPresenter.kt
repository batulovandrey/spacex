package com.example.butul0ve.spacex.presenter

import android.net.Uri
import com.example.butul0ve.spacex.adapter.FlightAdapter

/**
 * Created by butul0ve on 21.01.18.
 */

interface MainPresenter {

    fun showProgressBar()

    fun hideProgressBar()

    fun openYouTube(uri: Uri)

    fun getData()

    fun setAdapter(adapter: FlightAdapter)
}