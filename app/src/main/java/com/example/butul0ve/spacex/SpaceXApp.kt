package com.example.butul0ve.spacex

import android.app.Application
import android.content.Intent
import com.bumptech.glide.request.target.ViewTarget
import com.example.butul0ve.spacex.service.BackgroundNetworkService

class SpaceXApp: Application() {

    override fun onCreate() {
        super.onCreate()
        ViewTarget.setTagId(R.id.glide_tag)

        val intent = Intent(this, BackgroundNetworkService::class.java)
        startService(intent)
    }
}