package com.example.butul0ve.spacex.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by butul0ve on 20.01.18.
 */

data class Rocket(@SerializedName("rocket_id") var id: String,
                  @SerializedName("rocket_name") var name: String)