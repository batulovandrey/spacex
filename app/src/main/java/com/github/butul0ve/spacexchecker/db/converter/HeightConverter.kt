package com.github.butul0ve.spacexchecker.db.converter

import androidx.room.TypeConverter
import com.github.butul0ve.spacexchecker.db.model.Height

class HeightConverter {

    @TypeConverter
    fun fromHeight(height: Height): String {
        val sb = StringBuilder()
        sb.apply {
            append(height.meters)
            append(" ")
            append(height.feet)
        }
        return sb.toString()
    }

    @TypeConverter
    fun toHeight(string: String): Height {
        val list = string.split(" ")

        return Height(list[0].toFloatOrNull(), list[1].toFloatOrNull())
    }
}