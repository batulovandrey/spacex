package com.example.butul0ve.spacex.service

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.butul0ve.spacex.api.NetworkHelper
import com.example.butul0ve.spacex.db.DataManager
import kotlinx.coroutines.experimental.*
import java.io.IOException

const val ACTION_BACKGROUND_NETWORK_SERVICE = "BackgroundNetworkServiceResponse"
const val UPCOMING_LAUNCHES_EXTRA = "upcoming_launches_extra"
const val FLIGHTS_EXTRA = "flights_extra"
const val DRAGONS_EXTRA = "dragons_extra"

class BackgroundNetworkService : IntentService(BackgroundNetworkService::class.java.name) {

    private val TAG = BackgroundNetworkService::class.java.name

    private val networkHelper = NetworkHelper()
    private lateinit var dataManager: DataManager
    private lateinit var job: Job

    override fun onCreate() {
        super.onCreate()

        dataManager = DataManager(applicationContext)
        Log.d(TAG, "oncreate before delete")

        job = GlobalScope.launch {
            dataManager.deleteAllFlights()
            dataManager.deleteAllDragons()
        }

        Log.d(TAG, "oncreate after delete")
    }

    override fun onHandleIntent(intent: Intent?) {
        runBlocking { job.join() }

        GlobalScope.launch {

            sendPastLaunchesRequest()

            sendUpcomingLaunchesRequest()

            sendDragonsRequest()
        }
    }

    private fun sendUpcomingLaunchesRequest() {
        val responseIntent = Intent()
        responseIntent.action = ACTION_BACKGROUND_NETWORK_SERVICE
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT)

        val response = networkHelper.getUpcomingLaunches().execute()

        try {
            if (response.isSuccessful) {

                if (response.body() != null && response.body()?.isNotEmpty()!!) {
                    Log.d(TAG, "runblocking upcoming")
                    runBlocking {
                        GlobalScope.launch { dataManager.insertFlights(response.body()!!) }
                    }

                    responseIntent.putExtra(UPCOMING_LAUNCHES_EXTRA, true)
                    Log.d(TAG, "response body is ok")
                } else {
                    responseIntent.putExtra(UPCOMING_LAUNCHES_EXTRA, false)
                    Log.d(TAG, "response body upcoming launches is null or empty")
                }

            } else {
                responseIntent.putExtra(UPCOMING_LAUNCHES_EXTRA, false)
                Log.d(TAG, "onResponse upcoming launches failed")
            }
        } catch (ex: IOException) {
            responseIntent.putExtra(UPCOMING_LAUNCHES_EXTRA, false)
            Log.e(TAG, ex.message)
        } finally {
            sendBroadcast(responseIntent)
        }
    }

    private fun sendPastLaunchesRequest() {
        val responseIntent = Intent()
        responseIntent.action = ACTION_BACKGROUND_NETWORK_SERVICE
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT)

        val response = networkHelper.getFlights().execute()

        try {
            if (response.isSuccessful) {
                if (response.body() != null && response.body()?.isNotEmpty()!!) {
                    Log.d(TAG, "runblocking flights")
                    runBlocking {
                        GlobalScope.launch { dataManager.insertFlights(response.body()!!) }
                    }

                    responseIntent.putExtra(FLIGHTS_EXTRA, true)
                    Log.d(TAG, "response body flights is ok")
                } else {
                    responseIntent.putExtra(FLIGHTS_EXTRA, false)
                    Log.d(TAG, "response body flights is null or empty")
                }
            }
        } catch (ex: IOException) {
            responseIntent.putExtra(FLIGHTS_EXTRA, false)
            Log.e(TAG, ex.message)
        } finally {
            sendBroadcast(responseIntent)
        }
    }

    private fun sendDragonsRequest() {
        val responseIntent = Intent()
        responseIntent.action = ACTION_BACKGROUND_NETWORK_SERVICE
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT)

        val response = networkHelper.getDragons().execute()

        try {
            if (response.isSuccessful) {
                if (response.body() != null && response.body()?.isNotEmpty()!!) {
                    Log.d(TAG, "runblocking dragons")
                    runBlocking {
                        GlobalScope.launch { dataManager.insertDragons(response.body()!!) }
                    }

                    responseIntent.putExtra(DRAGONS_EXTRA, true)
                    Log.d(TAG, "response body dragons is ok")
                } else {
                    responseIntent.putExtra(DRAGONS_EXTRA, false)
                    Log.d(TAG, "response body dragons is null or empty")
                }
            }
        } catch (ex: IOException) {
            responseIntent.putExtra(DRAGONS_EXTRA, false)
            Log.e(TAG, ex.message)
        } finally {
            sendBroadcast(responseIntent)
        }
    }
}