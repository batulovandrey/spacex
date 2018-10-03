package com.example.butul0ve.spacex.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by butul0ve on 20.01.18.
 */

@Entity(tableName = "flights")
data class Flight(@PrimaryKey(autoGenerate = true) var flightId: Long?,
                  @SerializedName("flight_number") @ColumnInfo(name = "flight_number") var flightNumber: Int,
                  @SerializedName("rocket") @ColumnInfo(name = "rocket") var rocket: Rocket,
                  @SerializedName("launch_date_unix") @ColumnInfo(name = "launch_date_unix") var launchDate: String,
                  @SerializedName("links") @ColumnInfo(name = "links") var links: Links,
                  @SerializedName("details") @ColumnInfo(name = "details") var details: String?)