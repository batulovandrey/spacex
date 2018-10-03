package com.example.butul0ve.spacex.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.butul0ve.spacex.bean.Flight

interface FlightDao {

    @Query("select * from flights")
    fun getAll(): List<Flight>

    @Query("delete from flights")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(flight: Flight)
}