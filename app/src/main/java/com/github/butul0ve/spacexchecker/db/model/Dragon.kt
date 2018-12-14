package com.github.butul0ve.spacexchecker.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.github.butul0ve.spacexchecker.db.converter.ListConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "dragons")
data class Dragon(@PrimaryKey(autoGenerate = true) var dragonId: Long?,

                  @SerializedName("id")
                  @ColumnInfo(name = "id")
                  var id: String,

                  @SerializedName("name")
                  @ColumnInfo(name = "name")
                  var name: String,

                  @SerializedName("type")
                  @ColumnInfo(name = "type")
                  var type: String,

                  @SerializedName("active")
                  @ColumnInfo(name = "active")
                  var isActive: Boolean,

                  @SerializedName("wikipedia")
                  @ColumnInfo(name = "wikipedia")
                  var wiki: String,

                  @SerializedName("description")
                  @ColumnInfo(name = "description")
                  var description: String,

                  @SerializedName("first_flight")
                  @ColumnInfo(name = "first_flight")
                  var firstFlight: String,

                  @SerializedName("flickr_images")
                  @ColumnInfo(name = "flickr_images")
                  @TypeConverters(ListConverter::class)
                  var images: List<String>)