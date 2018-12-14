package com.github.butul0ve.spacexchecker.db.converter

import androidx.room.TypeConverter
import com.github.butul0ve.spacexchecker.db.model.Diameter

class DiameterConverter {

    @TypeConverter
    fun fromDiameter(diameter: Diameter): String {
        val sb = StringBuilder()
        sb.apply {
            append(diameter.meters)
            append(" ")
            append(diameter.feet)
        }
        return sb.toString()
    }

    @TypeConverter
    fun toDiameter(string: String): Diameter {
        val list = string.split(" ")

        return Diameter(list[0].toFloatOrNull(), list[1].toFloatOrNull())
    }
}