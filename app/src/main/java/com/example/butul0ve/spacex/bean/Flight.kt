package com.example.butul0ve.spacex.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by butul0ve on 20.01.18.
 */

data class Flight (@SerializedName("flight_number") var flightNumber: Int,
                   @SerializedName("rocket") var rocket: Rocket,
                   @SerializedName("launch_date_unix") var launchDate: String,
                   @SerializedName("links") var links: Links,
                   @SerializedName("details") var details: String)