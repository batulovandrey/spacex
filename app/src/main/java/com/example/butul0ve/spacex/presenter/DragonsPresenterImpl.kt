package com.example.butul0ve.spacex.presenter

import android.util.Log
import com.example.butul0ve.spacex.adapter.DragonAdapter
import com.example.butul0ve.spacex.api.NetworkHelper
import com.example.butul0ve.spacex.bean.Dragon
import com.example.butul0ve.spacex.view.RocketsView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DragonsPresenterImpl : DragonsPresenter {

    private val TAG = DragonsPresenterImpl::class.java.simpleName
    private lateinit var rocketsView: RocketsView
    private val networkHelper = NetworkHelper()

    override fun attachView(view: RocketsView) {
        rocketsView = view
    }

    override fun getData() {
        rocketsView.showProgressBar()
        networkHelper.getDragons().enqueue(object : Callback<List<Dragon>> {

            override fun onResponse(call: Call<List<Dragon>>?, response: Response<List<Dragon>>?) {
                Log.d(TAG, "response is successful ${response?.isSuccessful}")

                response?.isSuccessful?.let {
                    if (it) {
                        if (response.body() != null && response.body()?.isNotEmpty()!!) {
                            val adapter = DragonAdapter(response.body()!!)
                            rocketsView.setAdapter(adapter)
                            response.body()?.forEach {
                                Log.d(TAG, it.description)
                            }
                        } else {
                            Log.d(TAG, "response body is null or empty")
                        }
                    } else {
                        Log.d(TAG, response.errorBody()?.string())
                    }
                }
                rocketsView.hideProgressBar()
            }

            override fun onFailure(call: Call<List<Dragon>>?, t: Throwable?) {
                Log.d(TAG, t?.message)
                rocketsView.hideProgressBar()
            }


        })
    }
}