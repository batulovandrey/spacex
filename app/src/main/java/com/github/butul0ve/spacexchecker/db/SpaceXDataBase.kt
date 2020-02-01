package com.github.butul0ve.spacexchecker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.butul0ve.spacexchecker.db.converter.*
import com.github.butul0ve.spacexchecker.db.dao.DragonDao
import com.github.butul0ve.spacexchecker.db.dao.LaunchesDao
import com.github.butul0ve.spacexchecker.db.dao.LinkDao
import com.github.butul0ve.spacexchecker.db.dao.RocketDao
import com.github.butul0ve.spacexchecker.db.model.Dragon
import com.github.butul0ve.spacexchecker.db.model.Launch
import com.github.butul0ve.spacexchecker.db.model.Links
import com.github.butul0ve.spacexchecker.db.model.Rocket
import javax.inject.Inject

@Database(entities = [
    Dragon::class,
    Launch::class,
    Links::class,
    Rocket::class],
        version = 2,
        exportSchema = false)
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