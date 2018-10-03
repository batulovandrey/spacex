package com.example.butul0ve.spacex.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.butul0ve.spacex.bean.Dragon

interface DragonDao {

    @Query("select * from dragons")
    fun getAll(): List<Dragon>

    @Query("delete from dragons")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dragon: Dragon)
}