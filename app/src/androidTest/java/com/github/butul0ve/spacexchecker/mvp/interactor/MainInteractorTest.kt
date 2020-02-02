package com.github.butul0ve.spacexchecker.mvp.interactor

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.LargeTest
import com.github.butul0ve.spacexchecker.db.LaunchesRepository
import com.github.butul0ve.spacexchecker.db.SpaceXDataBase
import com.github.butul0ve.spacexchecker.db.model.Launch
import com.github.butul0ve.spacexchecker.network.OkHttpClientWithCache
import com.github.butul0ve.spacexchecker.network.api.NetworkHelper
import com.github.butul0ve.spacexchecker.network.api.ServerApi
import com.github.butul0ve.spacexchecker.network.api.ServerApiTest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins

import io.reactivex.schedulers.Schedulers
import org.junit.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.lang.reflect.Type
import java.util.concurrent.Callable

@LargeTest
class MainInteractorTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mainInteractor: MainMvpInteractor
    private lateinit var networkHelper: NetworkHelper
    private lateinit var repository: LaunchesRepository
    private lateinit var retrofit: Retrofit
    private lateinit var mockRetrofit: MockRetrofit
    private lateinit var typeTokenForListOfLaunches: Type

    companion object {

        @BeforeClass
        @JvmStatic
        fun before() {
            RxAndroidPlugins.reset()
            RxJavaPlugins.reset()
            RxJavaPlugins.setIoSchedulerHandler(object : io.reactivex.functions.Function<Scheduler, Scheduler> {
                override fun apply(t: Scheduler): Scheduler {
                    return Schedulers.trampoline()
                }
            })
            RxAndroidPlugins.setInitMainThreadSchedulerHandler(object : io.reactivex.functions.Function<Callable<Scheduler>, Scheduler> {
                override fun apply(t: Callable<Scheduler>): Scheduler {
                    return Schedulers.trampoline()
                }
            })
        }

        @AfterClass
        @JvmStatic
        fun after() {
            RxAndroidPlugins.reset()
            RxJavaPlugins.reset()
        }
    }

    @Before
    fun setUp() {
        retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://api.spacexdata.com/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClientWithCache.getInstance(ApplicationProvider.getApplicationContext()))
                .build()

        mockRetrofit = MockRetrofit.Builder(retrofit)
                .networkBehavior(NetworkBehavior.create())
                .build()

        val delegate = mockRetrofit.create(ServerApi::class.java)

        networkHelper = NetworkHelper(ServerApiTest(delegate))

        val db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                SpaceXDataBase::class.java)
                .allowMainThreadQueries()
                .build()

        repository = LaunchesRepository(db.launchesDao())

        mainInteractor = MainInteractor(networkHelper, repository)

        typeTokenForListOfLaunches = object : TypeToken<List<Launch>>() {}.type
    }
    
    @Test
    fun testGetPastFlightsFromServer() {
        val reader = javaClass.classLoader
                .getResourceAsStream("past_launches.json")
                .bufferedReader()

        val listOfPastLaunches = Gson().fromJson<List<Launch>>(reader.readText(),
                typeTokenForListOfLaunches)

        reader.close()

        val observer = mainInteractor.getPastFlightsFromServer()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .test()

        observer.await()

        observer.assertNoErrors()
                .assertValue(listOfPastLaunches)

        observer.dispose()
    }

    @Test
    fun testGetNextLaunch() {
        val type = object : TypeToken<Launch>() {}.type
        val reader = javaClass.classLoader
                .getResourceAsStream("next_launch.json")
                .bufferedReader()

        val expectedNextLaunch = Gson().fromJson<Launch>(reader.readText(), type)

        reader.close()

        val testObserver = mainInteractor.getNextLaunch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .test()

        testObserver.await()

        testObserver.assertNoErrors()
                .assertValue(expectedNextLaunch)

        testObserver.dispose()
    }

    /**
     * The method inserts data from the file into the db,
     * then makes a request to the db and compares the numbers of received
     * elements with the number of inserted elements (needed use the size
     * for the reason that the records in the db have an ID)
     */
    @Test
    fun testGetPastLaunchesFromDb() {
        val reader = javaClass.classLoader
                .getResourceAsStream("past_launches.json")
                .bufferedReader()

        val pastLaunches = Gson().fromJson<List<Launch>>(reader.readText(), typeTokenForListOfLaunches)

        reader.close()

        val testInsertObserver = repository.insertLaunches(pastLaunches)
                .subscribeOn(Schedulers.io())
                .test()

        testInsertObserver.await()

        testInsertObserver.assertNoErrors()

        val testObserver = mainInteractor.getPastLaunchesFromDb()
                .subscribeOn(Schedulers.io())
                .test()

        testObserver.assertNoErrors()
                .assertValue { it.size == pastLaunches.size }

        testObserver.dispose()
    }

    /**
     * Since the Completable is used to replace data in the db,
     * the TestObserver is initialized twice
     */
    @Test
    fun testReplacePastLaunches() {
        val reader = javaClass.classLoader
                .getResourceAsStream("past_launches.json")
                .bufferedReader()

        val pastLaunches = Gson().fromJson<List<Launch>>(reader.readText(), typeTokenForListOfLaunches)

        reader.close()

        val testInsertObserver = repository.insertLaunches(pastLaunches)
                .subscribeOn(Schedulers.io())
                .test()

        testInsertObserver.awaitTerminalEvent()

        testInsertObserver.assertNoErrors()

        var testObserver = mainInteractor.getPastLaunchesFromDb()
                .subscribeOn(Schedulers.io())
                .test()

        testObserver.assertNoErrors()
                .assertValue { it.size == pastLaunches.size }

        mainInteractor.replacePastLaunches(emptyList())
                .subscribeOn(Schedulers.io())
                .test()

        testObserver = mainInteractor.getPastLaunchesFromDb()
                .subscribeOn(Schedulers.io())
                .test()
        testObserver.assertNoErrors()
                .assertValue(emptyList())

        testObserver.dispose()
    }
}