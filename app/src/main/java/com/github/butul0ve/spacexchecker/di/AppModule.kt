package com.github.butul0ve.spacexchecker.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val app: Application) {

    @Provides
    fun provideContext(): Context {
        return app.applicationContext
    }
}