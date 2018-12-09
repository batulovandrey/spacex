package com.example.butul0ve.spacex.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import com.example.butul0ve.spacex.db.model.Launch
import com.example.butul0ve.spacex.db.model.Links
import com.example.butul0ve.spacex.db.model.Rocket
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LaunchesRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: SpaceXDataBase
    private lateinit var repository: LaunchesRepository

    @Before
    fun init() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                SpaceXDataBase::class.java)
                .allowMainThreadQueries()
                .build()
        repository = LaunchesRepository(db.launchesDao())
    }

    @After
    fun close() {
        db.close()
    }

    @Test
    fun testEmptyLaunches() {
        repository.getAllLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.isEmpty() }
    }

    @Test
    fun testGetAllLaunches() {
        repository.getAllLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.isEmpty() }

        repository.insertLaunches(getSomeLaunches())
                .test()
                .assertNoErrors()

        repository.getAllLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.size == getSomeLaunches().size }
    }

    @Test
    fun testInsertLaunch() {
        repository.getAllLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.isEmpty() }

        val launch = Launch(null,
                1,
                Rocket(null, "1", "rocket"),
                "10-10-2011",
                Links(null, "path", "link", "video"),
                "details",
                true)

        repository.insertLaunch(launch)
                .test()
        repository.getAllLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.size == 1 }
    }

    @Test
    fun testInsertLaunches() {
        repository.getAllLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.isEmpty() }

        repository.insertLaunches(getSomeLaunches())
                .test()

        repository.getAllLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.size == 4 }
    }

    @Test
    fun testGetAllUpcomingLaunches() {
        repository.getAllLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.isEmpty() }

        repository.insertLaunches(getSomeLaunches())
                .test()
                .assertNoErrors()

        repository.getAllLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.size == getSomeLaunches().size }

        repository.getAllUpcomingLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.size == 2 }
    }

    @Test
    fun testGetAllPastLaunches() {
        repository.getAllLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.isEmpty() }

        repository.insertLaunches(getSomeLaunches())
                .test()
                .assertNoErrors()

        repository.getAllLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.size == getSomeLaunches().size }

        repository.getAllPastLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.size == 2 }
    }

    @Test
    fun testDeleteAllLaunches() {
        repository.getAllLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.isEmpty() }

        repository.insertLaunches(getSomeLaunches())
                .test()
                .assertNoErrors()

        repository.getAllLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.size == getSomeLaunches().size }

        repository.deleteAllLaunches()
                .test()
                .assertNoErrors()

        repository.getAllLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.isEmpty() }
    }

    @Test
    fun testDeleteAllUpcomingLaunches() {
        repository.getAllLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.isEmpty() }

        repository.insertLaunches(getSomeLaunches())
                .test()
                .assertNoErrors()

        repository.getAllLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.size == getSomeLaunches().size }

        repository.deleteAllUpcomingLaunches()
                .test()
                .assertNoErrors()

        repository.getAllLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.size == 2 }

        repository.getAllUpcomingLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.isEmpty() }
    }

    @Test
    fun testDeleteAllPastLaunches() {
        repository.getAllLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.isEmpty() }

        repository.insertLaunches(getSomeLaunches())
                .test()
                .assertNoErrors()

        repository.getAllLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.size == getSomeLaunches().size }

        repository.deleteAllPastLaunches()
                .test()
                .assertNoErrors()

        repository.getAllLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.size == 2 }

        repository.getAllPastLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it.isEmpty() }
    }

    private fun getSomeLaunches(): List<Launch> {
        val upcomingLaunch1 = Launch(null,
                1,
                Rocket(null, "1", "rocket"),
                "10-10-2011",
                Links(null, "path", "link", "video"),
                "details",
                null)

        val pastLaunch1 = Launch(null,
                2,
                Rocket(null, "2", "rocket2"),
                "10-11-2011",
                Links(null, "path2", "link", "video"),
                "details2",
                true)

        val pastLaunch2 = Launch(null,
                3,
                Rocket(null, "3", "rocket3"),
                "10-11-2012",
                Links(null, "path3", "link", "video"),
                "details3",
                false)

        val upcomingLaunch2 = Launch(null,
                4,
                Rocket(null, "4", "rocket4"),
                "03-10-2011",
                Links(null, "path", "link", "video"),
                "details4",
                null)

        return listOf(upcomingLaunch1, upcomingLaunch2, pastLaunch1, pastLaunch2)
    }
}