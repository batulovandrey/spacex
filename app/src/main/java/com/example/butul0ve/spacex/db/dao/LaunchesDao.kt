package com.example.butul0ve.spacex.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.butul0ve.spacex.db.model.Launch
import io.reactivex.Flowable

@Dao
interface LaunchesDao {

    @Query("select * from launches")
    fun getAll(): Flowable<List<Launch>>

    @Query("select * from launches where launch_success = :isLaunchSuccess")
    fun getAllUpcomingLaunches(isLaunchSuccess: Boolean? = null): Flowable<List<Launch>>

    @Query("select * from launches where launch_success != :isLaunchSuccess")
    fun getAllPastLaunches(isLaunchSuccess: Boolean? = null): Flowable<List<Launch>>

    @Query("delete from launches")
    fun deleteAll()

    @Query("delete from launches where launch_success = :isLaunchSuccess")
    fun deleteAllUpcomingLaunches(isLaunchSuccess: Boolean? = null)

    @Query("delete from launches where launch_success != :isLaunchSuccess")
    fun deleteAllPastLaunches(isLaunchSuccess: Boolean? = null)

    @Insert(onConflict = REPLACE)
    fun insert(launch: Launch)

    @Insert(onConflict = REPLACE)
    fun insert(launches: List<Launch>)
}