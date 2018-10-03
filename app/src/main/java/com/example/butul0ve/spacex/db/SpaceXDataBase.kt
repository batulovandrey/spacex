package com.example.butul0ve.spacex.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.butul0ve.spacex.bean.Dragon
import com.example.butul0ve.spacex.bean.Flight
import com.example.butul0ve.spacex.bean.Links
import com.example.butul0ve.spacex.bean.Rocket
import com.example.butul0ve.spacex.db.dao.DragonDao
import com.example.butul0ve.spacex.db.dao.FlightDao
import com.example.butul0ve.spacex.db.dao.LinkDao
import com.example.butul0ve.spacex.db.dao.RocketDao

@Database(entities = [Dragon::class, Flight::class, Links::class, Rocket::class], version = 1)
abstract class SpaceXDataBase: RoomDatabase() {

    abstract fun dragonDao(): DragonDao
    abstract fun flightDao(): FlightDao
    abstract fun linkDao(): LinkDao
    abstract fun rocketDao(): RocketDao

    companion object {

        private var INSTANCE: SpaceXDataBase? = null

        fun getInstance(context: Context): SpaceXDataBase? {
            if (INSTANCE == null) {
                synchronized(SpaceXDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            SpaceXDataBase::class.java, "spacexapp.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}