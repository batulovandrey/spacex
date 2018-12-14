package com.github.butul0ve.spacexchecker.db.model

import androidx.room.ColumnInfo

data class Mass(@ColumnInfo(name = "kg")
                var kg: Long?,

                @ColumnInfo(name = "lb")
                var lb: Long?)