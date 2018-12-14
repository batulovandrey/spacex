package com.github.butul0ve.spacexchecker.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.github.butul0ve.spacexchecker.db.model.Dragon
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