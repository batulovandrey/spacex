package com.example.butul0ve.spacex.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.butul0ve.spacex.bean.Links

interface LinkDao {

    @Query("select * from links")
    fun getAll(): List<Links>

    @Query("delete from links")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(links: Links)
}