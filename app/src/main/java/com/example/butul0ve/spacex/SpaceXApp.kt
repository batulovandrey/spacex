package com.example.butul0ve.spacex

import android.app.Application
import com.bumptech.glide.request.target.ViewTarget

class SpaceXApp: Application() {

    override fun onCreate() {
        super.onCreate()
        ViewTarget.setTagId(R.id.glide_tag)
    }
}