package com.github.butul0ve.spacexchecker.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.github.butul0ve.spacexchecker.db.model.Rocket
import io.reactivex.Flowable

@Dao
interface RocketDao {

    @Query("select * from rockets")
    fun getAll(): Flowable<List<Rocket>>

    @Query("delete from rockets")
    fun deleteAll()

    @Insert(onConflict = REPLACE)
    fun insert(rocket: Rocket)

    @Insert(onConflict = REPLACE)
    fun insert(rockets: List<Rocket>)
}