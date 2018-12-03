package com.example.butul0ve.spacex.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.butul0ve.spacex.bean.UpcomingLaunch
import io.reactivex.Flowable

@Dao
interface UpcomingLaunchesDao {

    @Query("select * from upcoming_launches")
    fun getAll(): Flowable<List<UpcomingLaunch>>

    @Query("delete from upcoming_launches")
    fun deleteAll()

    @Insert(onConflict = REPLACE)
    fun insert(upcomingLaunches: List<UpcomingLaunch>)
}