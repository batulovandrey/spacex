package com.github.butul0ve.spacexchecker.di

import android.app.Application
import androidx.annotation.NonNull
import com.github.butul0ve.spacexchecker.mvp.MainActivity
import com.github.butul0ve.spacexchecker.mvp.fragment.DragonsFragment
import com.github.butul0ve.spacexchecker.mvp.fragment.MainFragment
import com.github.butul0ve.spacexchecker.mvp.fragment.RocketsFragment
import com.github.butul0ve.spacexchecker.mvp.fragment.UpcomingFragment
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

    fun inject(@NonNull rocketsFragment: RocketsFragment)
}