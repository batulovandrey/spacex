package com.example.butul0ve.spacex.db

import com.example.butul0ve.spacex.db.dao.DragonDao
import com.example.butul0ve.spacex.db.model.Dragon
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class DragonsRepository @Inject constructor(val dragonDao: DragonDao) {

    fun getAllDragonsFromDb(): Flowable<List<Dragon>> {
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