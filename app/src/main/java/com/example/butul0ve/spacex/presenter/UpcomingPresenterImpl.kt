package com.example.butul0ve.spacex.presenter

import android.util.Log
import com.example.butul0ve.spacex.adapter.FlightAdapter
import com.example.butul0ve.spacex.adapter.FlightClickListener
import com.example.butul0ve.spacex.api.NetworkHelper
import com.example.butul0ve.spacex.bean.Flight
import com.example.butul0ve.spacex.view.UpcomingView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingPresenterImpl: UpcomingPresenter, FlightClickListener {

    private val TAG = UpcomingPresenterImpl::class.java.name

    private lateinit var adapter: FlightAdapter
    private lateinit var flights: List<Flight>
    private lateinit var upcomingView: UpcomingView
    private val networkHelper = NetworkHelper()

    override fun attachView(view: UpcomingView) {
        upcomingView = view
    }

    override fun getData() {
        upcomingView.showProgressBar()
        networkHelper.getUpcomingLaunches().enqueue(object : Callback<List<Flight>> {

            override fun onResponse(call: Call<List<Flight>>?, response: Response<List<Flight>>?) {
                if (response?.isSuccessful!!) {

                    response.body()?.let {
                        flights = it
                        adapter = FlightAdapter(flights, this@UpcomingPresenterImpl)
                        upcomingView.setAdapter(adapter)
                    }
                } else {
                    Log.d(TAG, "failed")
                }

                upcomingView.hideProgressBar()
            }

            override fun onFailure(call: Call<List<Flight>>?, t: Throwable?) {
                upcomingView.hideProgressBar()
                Log.e(TAG, t?.message)
            }
        })
    }

    override fun onItemClick(position: Int) {
        // ignore
    }
}