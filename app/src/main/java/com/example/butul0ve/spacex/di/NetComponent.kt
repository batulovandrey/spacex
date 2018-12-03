package com.example.butul0ve.spacex.di

import android.app.Application
import androidx.annotation.NonNull
import com.example.butul0ve.spacex.mvp.fragment.DragonsFragment
import com.example.butul0ve.spacex.mvp.MainActivity
import com.example.butul0ve.spacex.mvp.fragment.MainFragment
import com.example.butul0ve.spacex.mvp.fragment.UpcomingFragment
import dagger.Component

@Component(modules = [
    AppModule::class,
    NetModule::class,
    DataModule::class])
interface NetComponent {

    fun inject(@NonNull app: Application)

    fun inject(@NonNull mainActivity: MainActivity)

    fun inject(@NonNull mainFragment: MainFragment)

    fun inject(@NonNull dragonsFragment: DragonsFragment)

    fun inject(@NonNull upcomingFragment: UpcomingFragment)
}