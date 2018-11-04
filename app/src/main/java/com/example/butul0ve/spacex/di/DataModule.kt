package com.example.butul0ve.spacex.di

import android.content.Context
import com.example.butul0ve.spacex.db.DataManager
import com.example.butul0ve.spacex.db.SpaceXDataBase
import com.example.butul0ve.spacex.network.api.NetworkHelper
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideSpaceXDatabase(context: Context): SpaceXDataBase {
        return SpaceXDataBase.getInstance(context)!!
    }

    @Provides
    fun provideDataManager(networkHelper: NetworkHelper,
                           spaceXDataBase: SpaceXDataBase): DataManager {
        return DataManager(networkHelper, spaceXDataBase)
    }
}