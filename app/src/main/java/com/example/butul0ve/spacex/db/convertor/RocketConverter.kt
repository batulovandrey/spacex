package com.example.butul0ve.spacex.db.convertor

import androidx.room.TypeConverter
import com.example.butul0ve.spacex.bean.Rocket

class RocketConverter {

    @TypeConverter
    fun fromRocket(rocket: Rocket): String {
        val sb = StringBuilder()
        sb.apply {
            append(rocket.rocketId)
            append(" ")
            append(rocket.id)
            append(" ")
            append(rocket.name)
        }
        return sb.toString()
    }

    @TypeConverter
    fun toRocket(string: String): Rocket {
        val list = string.split(" ")

        val rocketId = if (list[0] != "null") {
            list[0].toLong()
        } else {
            null
        }

        return Rocket(rocketId,
                list[1],
                list[2])
    }
}