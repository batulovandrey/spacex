package com.example.butul0ve.spacex.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by butul0ve on 20.01.18.
 */

data class Links(@SerializedName("mission_patch") var missionPath: String,
                 @SerializedName("article_link") var articleLink: String,
                 @SerializedName("video_link") var videoLink: String)