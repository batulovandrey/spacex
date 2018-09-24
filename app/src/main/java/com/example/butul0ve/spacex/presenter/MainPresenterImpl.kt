package com.example.butul0ve.spacex.presenter

import android.net.Uri
import android.util.Log
import com.example.butul0ve.spacex.adapter.FlightAdapter
import com.example.butul0ve.spacex.adapter.FlightClickListener
import com.example.butul0ve.spacex.api.NetworkHelper
import com.example.butul0ve.spacex.bean.Flight
import com.example.butul0ve.spacex.view.MainView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by butul0ve on 21.01.18.
 */

private val TAG = MainPresenterImpl::class.java.simpleName

class MainPresenterImpl(private val mMainView: MainView) : MainPresenter, FlightClickListener {

    private lateinit var flights: List<Flight>

    override fun onItemClick(position: Int) {
        if (::flights.isInitialized) {
            val flight = flights[position]
            val videoLink = Uri.parse(flight.links.videoLink)
            openYouTube(videoLink)
        }
    }

    private val networkHelper = NetworkHelper()

    override fun showProgressBar() {
        mMainView.showProgressBar()
    }

    override fun hideProgressBar() {
        mMainView.hideProgressBar()
    }

    override fun openYouTube(uri: Uri) {
        mMainView.openYouTube(uri)
    }

    override fun getData() {
        val flightsResponse = networkHelper.getFlights()
        mMainView.showProgressBar()

        flightsResponse.enqueue(object : Callback<List<Flight>> {

            override fun onResponse(call: Call<List<Flight>>?, response: Response<List<Flight>>?) {

                if (response?.isSuccessful!!) {
                    Log.d(TAG, "ok ${response.body()?.size}")

                    response.body()?.forEach {
                        Log.d(TAG, it.details)
                    }

                    response.body()?.let {
                        flights = it
                        val flightAdapter = FlightAdapter(flights, this@MainPresenterImpl)
                        mMainView.setAdapter(flightAdapter)
                    }

                } else {
                    Log.e(TAG, "failed")
                }

                mMainView.hideProgressBar()
            }

            override fun onFailure(call: Call<List<Flight>>?, t: Throwable?) {
                Log.e(TAG, t?.message)
                mMainView.hideProgressBar()
            }

        })
    }

    override fun setAdapter(adapter: FlightAdapter) {
        mMainView.setAdapter(adapter)
    }

    override fun showToast(message: Int) {
        mMainView.showToast(message)
    }

    override fun showButtonTryAgain() {
        mMainView.showButtonTryAgain()
    }

    override fun hideButtonTryAgain() {
        mMainView.hideButtonTryAgain()
    }
}