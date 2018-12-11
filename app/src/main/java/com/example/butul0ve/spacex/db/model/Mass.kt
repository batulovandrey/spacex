package com.example.butul0ve.spacex.db.model

import androidx.room.ColumnInfo

data class Mass(@ColumnInfo(name = "kg")
                var kg: Long?,

                @ColumnInfo(name = "lb")
                var lb: Long?)