package com.github.butul0ve.spacexchecker.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by butul0ve on 20.01.18.
 */

@Entity(tableName = "rockets")
data class Rocket(@PrimaryKey(autoGenerate = true)
                  var rocketId: Long?,

                  @SerializedName("rocket_id")
                  @ColumnInfo(name = "rocket_id")
                  var id: String,

                  @SerializedName("rocket_name")
                  @ColumnInfo(name = "rocket_name")
                  var name: String,

                  @SerializedName("active")
                  @ColumnInfo(name = "active")
                  var isActive: Boolean,

                  @SerializedName("stages")
                  @ColumnInfo(name = "stages")
                  var stages: Int,

                  @SerializedName("first_flight")
                  @ColumnInfo(name = "first_flight")
                  var firstFlight: String,

                  @SerializedName("wikipedia")
                  @ColumnInfo(name = "wikipedia")
                  var wiki: String,

                  @SerializedName("description")
                  @ColumnInfo(name = "description")
                  var description: String,

                  @SerializedName("height")
                  @ColumnInfo(name = "height")
                  var height: Height,

                  @SerializedName("diameter")
                  @ColumnInfo(name = "diameter")
                  var diameter: Diameter,

                  @SerializedName("mass")
                  @ColumnInfo(name = "mass")
                  var mass: Mass)