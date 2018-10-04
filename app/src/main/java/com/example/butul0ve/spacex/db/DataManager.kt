package com.example.butul0ve.spacex.db

import android.content.Context
import com.example.butul0ve.spacex.bean.Dragon
import com.example.butul0ve.spacex.bean.Flight
import io.reactivex.Flowable
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.async

class DataManager(context: Context) {

    private val dbInstance = SpaceXDataBase.getInstance(context)!!

    fun getDoneFlights(): Flowable<List<Flight>> {
        return dbInstance.flightDao().getDoneFlights()
    }

    suspend fun getNextFlights(): List<Flight> {
        return GlobalScope.async { dbInstance.flightDao().getNextFlights() }.await()
    }

    suspend fun getDragons(): List<Dragon> {
        return GlobalScope.async { dbInstance.dragonDao().getAll() }.await()
    }

    suspend fun deleteAllDragons() {
        GlobalScope.async { dbInstance.dragonDao().deleteAll() }.await()
    }

    suspend fun deleteAllFlights() {
        GlobalScope.async { dbInstance.flightDao().deleteAll() }.await()
    }

    suspend fun insertFlight(flight: Flight) {
        GlobalScope.async { dbInstance.flightDao().insert(flight) }.await()
    }

    suspend fun insertFlights(flights: List<Flight>) {
        GlobalScope.async { dbInstance.flightDao().insert(flights) }.await()
    }

    suspend fun insertDragon(dragon: Dragon) {
        GlobalScope.async { dbInstance.dragonDao().insert(dragon) }.await()
    }

    suspend fun insertDragons(dragons: List<Dragon>) {
        GlobalScope.async { dbInstance.dragonDao().insert(dragons) }.await()
    }
}