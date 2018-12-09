package com.example.butul0ve.spacex

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.bumptech.glide.request.target.ViewTarget
import com.example.butul0ve.spacex.di.*
import timber.log.Timber

class SpaceXApp: Application() {

    override fun onCreate() {
        super.onCreate()
        netComponent = initComponent()

        ViewTarget.setTagId(R.id.glide_tag)
        Timber.plant(Timber.DebugTree())
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
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