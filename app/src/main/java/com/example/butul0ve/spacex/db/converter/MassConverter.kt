package com.example.butul0ve.spacex.db.converter

import androidx.room.TypeConverter
import com.example.butul0ve.spacex.db.model.Mass

class MassConverter {

    @TypeConverter
    fun fromMass(mass: Mass): String {
        val sb = StringBuilder()
        sb.apply {
            append(mass.kg)
            append(" ")
            append(mass.lb)
        }
        return sb.toString()
    }

    @TypeConverter
    fun toMass(string: String): Mass {
        val list = string.split(" ")

        return Mass(list[0].toLongOrNull(), list[1].toLongOrNull())
    }
}