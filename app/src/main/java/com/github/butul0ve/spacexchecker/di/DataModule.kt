package com.github.butul0ve.spacexchecker.di

import android.content.Context
import com.github.butul0ve.spacexchecker.db.DragonsRepository
import com.github.butul0ve.spacexchecker.db.LaunchesRepository
import com.github.butul0ve.spacexchecker.db.RocketsRepository
import com.github.butul0ve.spacexchecker.db.SpaceXDataBase
import com.github.butul0ve.spacexchecker.db.dao.DragonDao
import com.github.butul0ve.spacexchecker.db.dao.LaunchesDao
import com.github.butul0ve.spacexchecker.db.dao.RocketDao
import com.github.butul0ve.spacexchecker.mvp.interactor.*
import com.github.butul0ve.spacexchecker.network.api.NetworkHelper
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
    fun provideRocketsDao(db: SpaceXDataBase): RocketDao {
        return db.rocketDao()
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
    fun provideRocketsRepositore(dao: RocketDao): RocketsRepository {
        return RocketsRepository(dao)
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

    @Provides
    fun provideRocketInteractor(networkHelper: NetworkHelper,
                                repository: RocketsRepository): RocketsMvpInteractor {
        return RocketsInteractor(networkHelper, repository)
    }
}