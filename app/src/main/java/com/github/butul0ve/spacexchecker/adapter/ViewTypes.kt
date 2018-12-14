package com.github.butul0ve.spacexchecker.adapter

import androidx.annotation.IntDef

class ViewTypes {

    @IntDef(ViewTypes.UPCOMING_LAUNCHES, ViewTypes.PAST_LAUNCHES)
    @Retention(AnnotationRetention.SOURCE)
    annotation class ViewHolderTypes

    companion object {
        const val UPCOMING_LAUNCHES = 0
        const val PAST_LAUNCHES = 1
    }
}