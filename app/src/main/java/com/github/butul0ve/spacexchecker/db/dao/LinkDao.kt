package com.github.butul0ve.spacexchecker.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.butul0ve.spacexchecker.db.model.Links

@Dao
interface LinkDao {

    @Query("select * from links")
    fun getAll(): List<Links>

    @Query("delete from links")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(links: Links)
}