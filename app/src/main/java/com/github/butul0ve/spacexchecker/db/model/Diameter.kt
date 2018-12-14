package com.github.butul0ve.spacexchecker.db.model

import androidx.room.ColumnInfo

data class Diameter(@ColumnInfo(name = "meters")
                    var meters: Float?,

                    @ColumnInfo(name = "feet")
                    var feet: Float?)