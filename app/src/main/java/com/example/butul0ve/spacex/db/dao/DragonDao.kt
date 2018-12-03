package com.example.butul0ve.spacex.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.butul0ve.spacex.bean.Dragon
import io.reactivex.Flowable

@Dao
interface DragonDao {

    @Query("select * from dragons")
    fun getAll(): Flowable<List<Dragon>>

    @Query("delete from dragons")
    fun deleteAll()

    @Insert(onConflict = REPLACE)
    fun insert(dragon: Dragon)

    @Insert(onConflict = REPLACE)
    fun insert(dragons: List<Dragon>)
}