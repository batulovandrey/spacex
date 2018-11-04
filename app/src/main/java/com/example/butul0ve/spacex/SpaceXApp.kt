package com.example.butul0ve.spacex

import android.app.Application
import com.bumptech.glide.request.target.ViewTarget
import com.example.butul0ve.spacex.di.*

class SpaceXApp: Application() {

    override fun onCreate() {
        super.onCreate()
        netComponent = initComponent()

        ViewTarget.setTagId(R.id.glide_tag)
    }

    private fun initComponent() : NetComponent {
        return DaggerNetComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule(JSON_URL))
                .dataModule(DataModule())
                .build()
    }

    companion object {

        lateinit var netComponent: NetComponent

        private const val JSON_URL = "https://api.spacexdata.com/v3/"

    }
}