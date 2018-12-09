package com.example.butul0ve.spacex.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.butul0ve.spacex.db.model.*
import com.example.butul0ve.spacex.db.converter.LinkConverter
import com.example.butul0ve.spacex.db.converter.ListConverter
import com.example.butul0ve.spacex.db.converter.RocketConverter
import com.example.butul0ve.spacex.db.dao.*
import javax.inject.Inject

@Database(entities = [
    Dragon::class,
    Launch::class,
    Links::class,
    Rocket::class],
        version = 1)
@TypeConverters(
        LinkConverter::class,
        ListConverter::class,
        RocketConverter::class)
abstract class SpaceXDataBase : RoomDatabase() {

    abstract fun dragonDao(): DragonDao
    abstract fun launchesDao(): LaunchesDao
    abstract fun linkDao(): LinkDao
    abstract fun rocketDao(): RocketDao

    companion object {

        private var INSTANCE: SpaceXDataBase? = null

        @Inject fun getInstance(context: Context): SpaceXDataBase? {
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