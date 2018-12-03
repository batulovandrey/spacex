package com.example.butul0ve.spacex.utils

import androidx.annotation.StringDef

@Retention(AnnotationRetention.SOURCE)
@StringDef(MAIN, DRAGONS, UPCOMING)
annotation class FragmentsDef

const val MAIN = "MainFragment"
const val DRAGONS = "DragonsFragment"
const val UPCOMING = "UpcomingFragment"