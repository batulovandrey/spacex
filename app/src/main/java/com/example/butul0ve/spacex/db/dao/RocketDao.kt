package com.example.butul0ve.spacex.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.butul0ve.spacex.bean.Rocket

@Dao
interface RocketDao {

    @Query("select * from rockets")
    fun getAll(): List<Rocket>

    @Query("delete from rockets")
    fun deleteAll()

    @Insert(onConflict = REPLACE)
    fun insert(rocket: Rocket)

    @Insert(onConflict = REPLACE)
    fun insert(rockets: List<Rocket>)
}