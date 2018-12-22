package com.github.butul0ve.spacexchecker.db

import com.github.butul0ve.spacexchecker.db.dao.DragonDao
import com.github.butul0ve.spacexchecker.db.model.Dragon
import io.reactivex.Completable
import io.reactivex.Maybe
import javax.inject.Inject

class DragonsRepository @Inject constructor(val dragonDao: DragonDao) {

    fun getAllDragonsFromDb(): Maybe<List<Dragon>> {
        return dragonDao.getAll()
    }

    fun deleteAllDragons(): Completable {
        return Completable.fromAction { dragonDao.deleteAll() }
    }

    fun insertDragon(dragon: Dragon): Completable {
        return Completable.fromAction { dragonDao.insert(dragon) }
    }

    fun insertDragons(dragons: List<Dragon>): Completable {
        return Completable.fromAction { dragonDao.insert(dragons) }
    }
}