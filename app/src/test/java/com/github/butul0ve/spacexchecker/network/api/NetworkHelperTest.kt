package com.github.butul0ve.spacexchecker.network.api

import com.github.butul0ve.spacexchecker.db.model.*
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
                Rocket(null,
                        "1",
                        "rocket",
                        true,
                        3,
                        "11/12/2011",
                        "wiki",
                        "description",
                        Height(1f, 0.5f),
                        Diameter(2f, 3f),
                        Mass(1L, 3L)),
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
        val upcomingLaunch1 = Launch(null,
                1,
                Rocket(null,
                        "1",
                        "rocket",
                        true,
                        3,
                        "11/12/2011",
                        "wiki",
                        "description",
                        Height(1f, 0.5f),
                        Diameter(2f, 3f),
                        Mass(1L, 3L)),
                "10-10-2011",
                Links(null, "path", "link", "video"),
                "details",
                null)

        val upcomingLaunch2 = Launch(null,
                4,
                Rocket(null,
                        "4",
                        "rocket4",
                        true,
                        2,
                        "05/10/2013",
                        "wiki",
                        "description",
                        Height(10f, 20f),
                        Diameter(5f, 10f),
                        Mass(5L, 10L)),
                "03-10-2011",
                Links(null, "path", "link", "video"),
                "details4",
                null)

        return listOf(upcomingLaunch1, upcomingLaunch2)
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
        val pastLaunch1 = Launch(null,
                2,
                Rocket(null,
                        "2",
                        "rocket2",
                        false,
                        2,
                        "11/10/2009",
                        "wiki",
                        "desc",
                        Height(10f, 20f),
                        Diameter(5f, 10f),
                        Mass(2L, 5L)),
                "10-11-2011",
                Links(null, "path2", "link", "video"),
                "details2",
                true)

        val pastLaunch2 = Launch(null,
                3,
                Rocket(null,
                        "3",
                        "rocket3",
                        true,
                        5,
                        "10/09/2010",
                        "wikipedia",
                        "some description",
                        Height(10f, 20f),
                        Diameter(50f, 120f),
                        Mass(5L, 10L)),
                "10-11-2012",
                Links(null, "path3", "link", "video"),
                "details3",
                false)

        return listOf(pastLaunch1, pastLaunch2)
    }
}