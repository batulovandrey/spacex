package com.github.butul0ve.spacexchecker.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by butul0ve on 20.01.18.
 */

@Entity(tableName = "links")
data class Links(@PrimaryKey(autoGenerate = true)
                 var linkId: Long?,

                 @SerializedName("mission_patch_small")
                 @ColumnInfo(name = "mission_patch_small")
                 var missionPathSmall: String,

                 @SerializedName("article_link")
                 @ColumnInfo(name = "article_link")
                 var articleLink: String?,

                 @SerializedName("video_link")
                 @ColumnInfo(name = "video_link")
                 var videoLink: String?,

                 @SerializedName("reddit_campaing")
                 @ColumnInfo(name = "reddit_campaing")
                 var redditCampaing: String?,

                 @SerializedName("reddit_launch")
                 @ColumnInfo(name = "reddit_launch")
                 var redditLaunch: String?)