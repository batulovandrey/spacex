package com.example.butul0ve.spacex

import android.app.Application
import com.bumptech.glide.request.target.ViewTarget
import com.example.butul0ve.spacex.db.DataManager

class SpaceXApp: Application() {

    override fun onCreate() {
        super.onCreate()
        ViewTarget.setTagId(R.id.glide_tag)
    }

    companion object {

        var dataManager: DataManager? = null
    }
}