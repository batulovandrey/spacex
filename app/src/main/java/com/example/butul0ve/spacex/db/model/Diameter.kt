package com.example.butul0ve.spacex.db.model

import androidx.room.ColumnInfo

data class Diameter(@ColumnInfo(name = "meters")
                    var meters: Float?,

                    @ColumnInfo(name = "feet")
                    var feet: Float?)