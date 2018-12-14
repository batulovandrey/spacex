package com.example.butul0ve.spacex.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.butul0ve.spacex.db.model.Dragon
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DragonsRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: SpaceXDataBase
    private lateinit var repository: DragonsRepository

    @Before
    fun init() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                SpaceXDataBase::class.java)
                .allowMainThreadQueries()
                .build()
        repository = DragonsRepository(db.dragonDao())
    }

    @After
    fun close() {
        db.close()
    }

    @Test
    fun testEmptyDragons() {
        repository.getAllDragonsFromDb()
                .test()
                .assertNoErrors()
                .assertValue { it.isEmpty() }
    }

    @Test
    fun testInsertDragon() {
        repository.getAllDragonsFromDb()
                .test()
                .assertNoErrors()
                .assertValue { it.isEmpty() }

        val dragon = Dragon(null,
                "id1",
                "dragon",
                "type",
                true,
                "link to wiki",
                "description",
                "date of first flight",
                listOf("link to the images"))

        repository.insertDragon(dragon)
                .test()
                .assertNoErrors()

        repository.getAllDragonsFromDb()
                .test()
                .assertNoErrors()
                .assertValue { it.size == 1 }
    }

    @Test
    fun testInsertDragons() {
        repository.getAllDragonsFromDb()
                .test()
                .assertNoErrors()
                .assertValue { it.isEmpty() }

        repository.insertDragons(getSomeDragons())
                .test()
                .assertNoErrors()

        repository.getAllDragonsFromDb()
                .test()
                .assertNoErrors()
                .assertValue { it.size == getSomeDragons().size }
    }

    @Test
    fun testDeleteDragons() {
        repository.getAllDragonsFromDb()
                .test()
                .assertNoErrors()
                .assertValue { it.isEmpty() }

        repository.insertDragons(getSomeDragons())
                .test()
                .assertNoErrors()

        repository.getAllDragonsFromDb()
                .test()
                .assertNoErrors()
                .assertValue { it.size == getSomeDragons().size }

        repository.deleteAllDragons()
                .test()
                .assertNoErrors()

        repository.getAllDragonsFromDb()
                .test()
                .assertNoErrors()
                .assertValue { it.isEmpty() }
    }

    private fun getSomeDragons(): List<Dragon> {
        val dragon1 = Dragon(null,
                "id1",
                "dragon",
                "type",
                true,
                "link to wiki",
                "description",
                "date of first flight",
                listOf("link to the images"))

        val dragon2 = Dragon(null,
                "id2",
                "dragon2",
                "type2",
                true,
                "link to wiki2",
                "description2",
                "date of first flight2",
                listOf("link to the images1", "link to the images2"))

        return listOf(dragon1, dragon2)
    }
}