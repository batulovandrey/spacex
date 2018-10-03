package com.example.butul0ve.spacex.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by butul0ve on 20.01.18.
 */

@Entity(tableName = "links")
data class Links(@PrimaryKey(autoGenerate = true) var linkId: Long?,
                 @SerializedName("mission_patch") @ColumnInfo(name = "mission_patch") var missionPath: String,
                 @SerializedName("article_link") @ColumnInfo(name = "article_link") var articleLink: String,
                 @SerializedName("video_link") @ColumnInfo(name = "video_link") var videoLink: String)