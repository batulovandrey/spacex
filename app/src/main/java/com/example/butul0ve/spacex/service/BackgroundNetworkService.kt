package com.example.butul0ve.spacex.service

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.butul0ve.spacex.api.NetworkHelper
import com.example.butul0ve.spacex.db.DataManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.*

const val ACTION_BACKGROUND_NETWORK_SERVICE = "BackgroundNetworkServiceResponse"
const val UPCOMING_LAUNCHES_EXTRA = "upcoming_launches_extra"
const val FLIGHTS_EXTRA = "flights_extra"
const val DRAGONS_EXTRA = "dragons_extra"

class BackgroundNetworkService : IntentService(BackgroundNetworkService::class.java.name) {

    private val TAG = BackgroundNetworkService::class.java.name

    private val networkHelper = NetworkHelper()
    private val disposable = CompositeDisposable()
    private lateinit var dataManager: DataManager
    private lateinit var job: Job

    override fun onCreate() {
        super.onCreate()

        dataManager = DataManager(applicationContext)
        Log.d(TAG, "oncreate before delete")

        job = GlobalScope.launch {
            dataManager.deleteAllPastLaunches()
            dataManager.deleteAllDragons()
            dataManager.deleteAllUpcomingLaunches()
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
        disposable.add(networkHelper.getUpcomingLaunches()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    dataManager.insertUpcomingLaunches(it).subscribe()
                    val responseIntent = Intent()
                    responseIntent.action = ACTION_BACKGROUND_NETWORK_SERVICE
                    responseIntent.addCategory(Intent.CATEGORY_DEFAULT)
                    responseIntent.putExtra(UPCOMING_LAUNCHES_EXTRA, true)
                    sendBroadcast(responseIntent)
                    Log.d(TAG, "send upcoming subscribe, size is ${it.size}")
                },
                        {
                            val responseIntent = Intent()
                            responseIntent.action = ACTION_BACKGROUND_NETWORK_SERVICE
                            responseIntent.addCategory(Intent.CATEGORY_DEFAULT)
                            responseIntent.putExtra(UPCOMING_LAUNCHES_EXTRA, false)
                            sendBroadcast(responseIntent)

                        }))
    }

    private fun sendPastLaunchesRequest() {
        disposable.add(networkHelper.getFlights()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    dataManager.insertPastLaunches(it).subscribe()
                    val responseIntent = Intent()
                    responseIntent.action = ACTION_BACKGROUND_NETWORK_SERVICE
                    responseIntent.addCategory(Intent.CATEGORY_DEFAULT)
                    responseIntent.putExtra(FLIGHTS_EXTRA, true)
                    sendBroadcast(responseIntent)
                    Log.d(TAG, "send past subscribe, size is ${it.size}")
                },
                        {
                            val responseIntent = Intent()
                            responseIntent.action = ACTION_BACKGROUND_NETWORK_SERVICE
                            responseIntent.addCategory(Intent.CATEGORY_DEFAULT)
                            responseIntent.putExtra(FLIGHTS_EXTRA, false)
                            sendBroadcast(responseIntent)
                        }))
    }

    private fun sendDragonsRequest() {
        disposable.add(networkHelper.getDragons()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    dataManager.insertDragons(it).subscribe()
                    val responseIntent = Intent()
                    responseIntent.action = ACTION_BACKGROUND_NETWORK_SERVICE
                    responseIntent.addCategory(Intent.CATEGORY_DEFAULT)
                    responseIntent.putExtra(DRAGONS_EXTRA, true)
                    sendBroadcast(responseIntent)
                    Log.d(TAG, "send dragons subscribe, size is ${it.size}")
                },
                        {
                            val responseIntent = Intent()
                            responseIntent.action = ACTION_BACKGROUND_NETWORK_SERVICE
                            responseIntent.addCategory(Intent.CATEGORY_DEFAULT)
                            responseIntent.putExtra(DRAGONS_EXTRA, true)
                            sendBroadcast(responseIntent)
                        }))
    }
}