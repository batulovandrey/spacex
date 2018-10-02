package com.example.butul0ve.spacex.utils

import androidx.annotation.StringDef

@Retention(AnnotationRetention.SOURCE)
@StringDef(MAIN, DRAGONS, UPCOMING)
annotation class FragmentsDef

const val MAIN = "main"
const val DRAGONS = "dragons"
const val UPCOMING = "upcoming"