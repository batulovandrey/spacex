package com.example.butul0ve.spacex.di

import android.content.Context
import com.example.butul0ve.spacex.db.DragonsRepository
import com.example.butul0ve.spacex.db.LaunchesRepository
import com.example.butul0ve.spacex.db.SpaceXDataBase
import com.example.butul0ve.spacex.db.dao.DragonDao
import com.example.butul0ve.spacex.db.dao.LaunchesDao
import com.example.butul0ve.spacex.mvp.interactor.*
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
    fun provideLaunchesDao(db: SpaceXDataBase): LaunchesDao {
        return db.launchesDao()
    }

    @Provides
    fun provideDragonsDao(db: SpaceXDataBase): DragonDao {
        return db.dragonDao()
    }

    @Provides
    fun provideLaunchesRepository(dao: LaunchesDao): LaunchesRepository {
        return LaunchesRepository(dao)
    }

    @Provides
    fun provideDragonsRepository(dao: DragonDao): DragonsRepository {
        return DragonsRepository(dao)
    }

    @Provides
    fun provideMainInteractor(networkHelper: NetworkHelper,
                              repository: LaunchesRepository) : MainMvpInteractor {
        return MainInteractor(networkHelper, repository)
    }

    @Provides
    fun provideUpcomingInteractor(networkHelper: NetworkHelper,
                                  repository: LaunchesRepository): UpcomingMvpInteractor {
        return UpcomingInteractor(networkHelper, repository)
    }

    @Provides
    fun provideDragonsInteractor(networkHelper: NetworkHelper,
                                 repository: DragonsRepository): DragonsMvpInteractor {
        return DragonsInteractor(networkHelper, repository)
    }
}