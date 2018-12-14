package com.github.butul0ve.spacexchecker.db.model

import androidx.room.ColumnInfo

data class Height(@ColumnInfo(name = "meters")
                  var meters: Float?,

                  @ColumnInfo(name = "feet")
                  var feet: Float?)