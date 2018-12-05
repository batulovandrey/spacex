package com.example.butul0ve.spacex.db.converter

import androidx.room.TypeConverter
import com.example.butul0ve.spacex.db.model.Links

class LinkConverter {

    @TypeConverter
    fun fromLink(links: Links): String {
        val sb = StringBuilder()
        sb.apply {
            append(links.linkId)
            append(" ")
            append(links.missionPath)
            append(" ")
            append(links.articleLink)
            append(" ")
            append(links.videoLink)
        }
        return sb.toString()
    }

    @TypeConverter
    fun toLink(string: String): Links {
        val list = string.split(" ")

        val linkId = if (list[0] != "null") {
            list[0].toLong()
        } else {
            null
        }

        return Links(linkId,
                list[1],
                list[2],
                list[3])
    }
}