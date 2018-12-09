package com.example.butul0ve.spacex.network.api

import com.example.butul0ve.spacex.db.model.Dragon
import com.example.butul0ve.spacex.db.model.Launch
import com.example.butul0ve.spacex.db.model.Links
import com.example.butul0ve.spacex.db.model.Rocket
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class NetworkHelperTest {

    private lateinit var networkHelper: NetworkHelper
    private lateinit var launch: Launch
    private lateinit var upcomingLaunches: List<Launch>
    private lateinit var dragons: List<Dragon>
    private lateinit var pastLaunches: List<Launch>
    private var currentYear = Calendar.getInstance().get(Calendar.YEAR)

    @Mock
    private lateinit var serverApi: ServerApi

    @Before
    fun init() {
        launch = Launch(1L,
                1,
                Rocket(1L, "1", "rocket"),
                "launch date",
                Links(1L, "path", "article", "video"),
                "details",
                true)

        upcomingLaunches = getSomeUpcomingLaunches()
        dragons = getSomeDragons()
        pastLaunches = getSomePastLaunches()

        `when`(serverApi.getNextLaunch())
                .thenReturn(Single.just(launch))
        `when`(serverApi.getUpcomingLaunches())
                .thenReturn(Single.just(upcomingLaunches))
        `when`(serverApi.getDragons())
                .thenReturn(Single.just(dragons))
        `when`(serverApi.getLaunchesByYear(currentYear))
                .thenReturn(Single.just(pastLaunches))

        networkHelper = NetworkHelper(serverApi)
    }

    @Test
    fun testGetNext() {
        networkHelper.getNext()
                .test()
                .assertNoErrors()
                .assertValue { it == launch }
    }

    @Test
    fun testGetUpcomingLaunches() {
        networkHelper.getUpcomingLaunches()
                .test()
                .assertNoErrors()
                .assertValue { it == upcomingLaunches }
    }

    @Test
    fun testGetDragons() {
        networkHelper.getDragons()
                .test()
                .assertNoErrors()
                .assertValue { it == dragons }
    }

    @Test
    fun testGetFlights() {
        networkHelper.getFlights(currentYear)
                .test()
                .assertNoErrors()
                .assertValue { it == pastLaunches }
    }

    private fun getSomeUpcomingLaunches(): List<Launch> {
        val launch1 = Launch(2L,
                1,
                Rocket(2L, "2", "rocket"),
                "launch date",
                Links(1L, "path", "article", "video"),
                "details",
                null)

        val launch2 = Launch(3L,
                2,
                Rocket(3L, "3", "rocket"),
                "launch date",
                Links(1L, "path", "article", "video"),
                "details",
                null)

        return listOf(launch1, launch2)
    }

    private fun getSomeDragons(): List<Dragon> {
        val dragon1 = Dragon(1L,
                "1",
                "dragon1",
                "type",
                true,
                "wiki",
                "desc",
                "date of first flight",
                listOf("some images"))

        val dragon2 = Dragon(2L,
                "2",
                "dragon2",
                "type",
                true,
                "wiki",
                "desc",
                "date of first flight",
                listOf("some images", "some images2"))

        return listOf(dragon1, dragon2)
    }

    private fun getSomePastLaunches(): List<Launch> {
        val launch1 = Launch(2L,
                1,
                Rocket(2L, "2", "rocket"),
                "launch date",
                Links(1L, "path", "article", "video"),
                "details",
                true)

        val launch2 = Launch(3L,
                2,
                Rocket(3L, "3", "rocket"),
                "launch date",
                Links(1L, "path", "article", "video"),
                "details",
                false)

        return listOf(launch1, launch2)
    }
}