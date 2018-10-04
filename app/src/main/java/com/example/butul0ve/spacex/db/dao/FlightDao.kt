package com.example.butul0ve.spacex.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.butul0ve.spacex.bean.Flight
import io.reactivex.Flowable

@Dao
interface FlightDao {

    @Query("select * from flights")
    fun getAll(): Flowable<Flight>

    @Query("select * from flights where launch_success =:isSuccess")
    fun getDoneFlights(isSuccess: Boolean = true): Flowable<List<Flight>>

    @Query("select * from flights where launch_success =:isSuccess")
    fun getNextFlights(isSuccess: Boolean? = null): List<Flight>

    @Query("delete from flights")
    fun deleteAll()

    @Insert(onConflict = REPLACE)
    fun insert(flight: Flight)

    @Insert(onConflict = REPLACE)
    fun insert(flights: List<Flight>)
}