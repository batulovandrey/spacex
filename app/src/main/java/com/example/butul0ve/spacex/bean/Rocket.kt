package com.example.butul0ve.spacex.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by butul0ve on 20.01.18.
 */

@Entity(tableName = "rockets")
data class Rocket(@PrimaryKey(autoGenerate = true) var rocketId: Long?,
                  @SerializedName("rocket_id") @ColumnInfo(name ="rocket_id") var id: String,
                  @SerializedName("rocket_name") @ColumnInfo(name = "rocket_name") var name: String)