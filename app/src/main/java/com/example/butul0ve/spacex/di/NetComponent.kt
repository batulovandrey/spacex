package com.example.butul0ve.spacex.di

import android.app.Application
import androidx.annotation.NonNull
import com.example.butul0ve.spacex.MainActivity
import dagger.Component

@Component(modules = [
    AppModule::class,
    NetModule::class,
    DataModule::class])
interface NetComponent {

    fun inject(@NonNull app: Application)

    fun inject(@NonNull mainActivity: MainActivity)
}