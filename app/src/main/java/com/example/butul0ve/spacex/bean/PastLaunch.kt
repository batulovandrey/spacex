package com.example.butul0ve.spacex.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.butul0ve.spacex.db.convertor.LinkConverter
import com.example.butul0ve.spacex.db.convertor.RocketConverter
import com.google.gson.annotations.SerializedName

/**
 * Created by butul0ve on 20.01.18.
 */

@Entity(tableName = "past_launches")
data class PastLaunch(@PrimaryKey(autoGenerate = true)
                      var flightId: Long?,

                      @SerializedName("flight_number")
                      @ColumnInfo(name = "flight_number")
                      var flightNumber: Int,

                      @SerializedName("rocket")
                      @ColumnInfo(name = "rocket")
                      @TypeConverters(RocketConverter::class)
                      var rocket: Rocket,

                      @SerializedName("launch_date_unix")
                      @ColumnInfo(name = "launch_date_unix")
                      var launchDate: String,

                      @SerializedName("links")
                      @ColumnInfo(name = "links")
                      @TypeConverters(LinkConverter::class)
                      var links: Links,

                      @SerializedName("details")
                      @ColumnInfo(name = "details")
                      var details: String?,

                      @SerializedName("launch_success")
                      @ColumnInfo(name = "launch_success")
                      var isLaunchSuccess: Boolean?)