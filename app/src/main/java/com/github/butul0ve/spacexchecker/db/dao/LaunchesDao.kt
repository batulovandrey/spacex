package com.github.butul0ve.spacexchecker.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.github.butul0ve.spacexchecker.db.model.Launch
import io.reactivex.Flowable

@Dao
interface LaunchesDao {

    @Query("select * from launches")
    fun getAll(): Flowable<List<Launch>>

    @Query("select * from launches where launch_success is null")
    fun getAllUpcomingLaunches(): Flowable<List<Launch>>

    @Query("select * from launches where launch_success is not null")
    fun getAllPastLaunches(): Flowable<List<Launch>>

    @Query("delete from launches")
    fun deleteAll()

    @Query("delete from launches where launch_success is null")
    fun deleteAllUpcomingLaunches()

    @Query("delete from launches where launch_success is not null")
    fun deleteAllPastLaunches()

    @Insert(onConflict = REPLACE)
    fun insert(launch: Launch)

    @Insert(onConflict = REPLACE)
    fun insert(launches: List<Launch>)
}