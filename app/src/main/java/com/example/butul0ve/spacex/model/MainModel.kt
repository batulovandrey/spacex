package com.example.butul0ve.spacex.model

import android.net.Uri
import com.example.butul0ve.spacex.ExecutionThread
import com.example.butul0ve.spacex.adapter.FlightAdapter
import com.example.butul0ve.spacex.adapter.FlightClickListener
import com.example.butul0ve.spacex.bean.Flight
import com.example.butul0ve.spacex.presenter.MainPresenter

/**
 * Created by butul0ve on 21.01.18.
 */

class MainModel(private val mMainPresenter: MainPresenter) : ExecutionThread.CustomCallback, FlightClickListener {

    private lateinit var mFlights: List<Flight>
    private lateinit var mAdapter: FlightAdapter

    fun getData() {
        ExecutionThread(this).start()
    }

    override fun onItemClick(position: Int) {
        val flight = mFlights[position]
        val videoLink = Uri.parse(flight.links.videoLink)
        mMainPresenter.openYouTube(videoLink)
    }

    override fun setFlights(flights: List<Flight>?) {
        mFlights = flights!!
        mAdapter = FlightAdapter(mFlights, this)
        mMainPresenter.setAdapter(mAdapter)
    }

    override fun showProgressBar() {
        mMainPresenter.showProgressBar()
    }

    override fun hideProgressBar() {
        mMainPresenter.hideProgressBar()
    }
}