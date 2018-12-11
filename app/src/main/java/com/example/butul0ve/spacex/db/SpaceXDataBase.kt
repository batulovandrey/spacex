package com.example.butul0ve.spacex.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.butul0ve.spacex.db.converter.*
import com.example.butul0ve.spacex.db.dao.DragonDao
import com.example.butul0ve.spacex.db.dao.LaunchesDao
import com.example.butul0ve.spacex.db.dao.LinkDao
import com.example.butul0ve.spacex.db.dao.RocketDao
import com.example.butul0ve.spacex.db.model.Dragon
import com.example.butul0ve.spacex.db.model.Launch
import com.example.butul0ve.spacex.db.model.Links
import com.example.butul0ve.spacex.db.model.Rocket
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
        RocketConverter::class,
        HeightConverter::class,
        MassConverter::class,
        DiameterConverter::class)
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