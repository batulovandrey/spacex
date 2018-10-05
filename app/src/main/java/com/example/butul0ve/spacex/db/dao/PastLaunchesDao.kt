package com.example.butul0ve.spacex.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.butul0ve.spacex.bean.PastLaunch
import io.reactivex.Flowable

@Dao
interface PastLaunchesDao {

    @Query("select * from past_launches")
    fun getAll(): Flowable<List<PastLaunch>>

    @Query("delete from past_launches")
    fun deleteAll()

    @Insert(onConflict = REPLACE)
    fun insert(pastLaunch: PastLaunch)

    @Insert(onConflict = REPLACE)
    fun insert(pastLaunches: List<PastLaunch>)
}