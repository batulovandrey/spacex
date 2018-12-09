package com.example.butul0ve.spacex.db.converter

import androidx.room.TypeConverter

class ListConverter {

    @TypeConverter
    fun fromList(images: List<String>): String {
        return images.joinToString(separator = ", ")
    }

    @TypeConverter
    fun toLIst(string: String): List<String> {
        return string.split(", ")
    }
}