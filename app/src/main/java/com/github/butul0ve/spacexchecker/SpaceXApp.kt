package com.github.butul0ve.spacexchecker

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.github.butul0ve.spacexchecker.di.*
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

class SpaceXApp: Application() {

    override fun onCreate() {
        super.onCreate()
        netComponent = initComponent()

        Timber.plant(Timber.DebugTree())
        AndroidThreeTen.init(this)
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