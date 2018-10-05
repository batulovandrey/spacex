package com.example.butul0ve.spacex

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.butul0ve.spacex.db.DataManager
import com.example.butul0ve.spacex.presenter.MainPresenterImpl
import com.example.butul0ve.spacex.service.*
import com.example.butul0ve.spacex.utils.MAIN
import com.example.butul0ve.spacex.utils.DRAGONS
import com.example.butul0ve.spacex.utils.UPCOMING
import com.example.butul0ve.spacex.utils.convert
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val CURRENT_FRAGMENT = "current_fragment"

class MainActivity : AppCompatActivity(), MainFragment.OnItemClickListener {

    private val TAG = MainActivity::class.java.simpleName

    private var currentFragment: Fragment? = null
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var placeHolderIV: ImageView

    private val backgroundBroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(ctx: Context?, intent: Intent?) {
            if (intent == null) return

            setupBottomNavigationView()
            setCheckedItemOfBottomNavigationView()
            placeHolderIV.visibility = View.GONE

            if (intent.action == ACTION_BACKGROUND_NETWORK_SERVICE) {

                Log.d(TAG, "upcoming extra launches is ${intent.getBooleanExtra(UPCOMING_LAUNCHES_EXTRA, false)}")
                if (intent.getBooleanExtra(UPCOMING_LAUNCHES_EXTRA, false)) {
                    currentFragment = MainFragment()
                    val manager = supportFragmentManager

                    manager.beginTransaction().apply {
                        replace(R.id.container, currentFragment as MainFragment)
                        commit()
                    }

                    (currentFragment as MainFragment).setPresenter(MainPresenterImpl(DataManager(this@MainActivity)))
                    setCheckedItemOfBottomNavigationView()
                }
                Log.d(TAG, "flights extra launches is ${intent.getBooleanExtra(FLIGHTS_EXTRA, false)}")
                Log.d(TAG, "dragons extra is ${intent.getBooleanExtra(DRAGONS_EXTRA, false)}")

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeHolderIV = findViewById(R.id.placeholder_iv)

        val manager = supportFragmentManager

        if (savedInstanceState == null) {
            placeHolderIV.visibility = View.VISIBLE
        } else {
            placeHolderIV.visibility = View.GONE
            setupBottomNavigationView()
            setCheckedItemOfBottomNavigationView()

            currentFragment = manager.findFragmentById(R.id.container)
            if (currentFragment == null) {
                currentFragment = savedInstanceState.getString(CURRENT_FRAGMENT, MAIN).convert(this)
                manager.beginTransaction().apply {
                    replace(R.id.container, currentFragment!!)
                    commit()
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter(ACTION_BACKGROUND_NETWORK_SERVICE)
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT)
        registerReceiver(backgroundBroadcastReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(backgroundBroadcastReceiver)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(CURRENT_FRAGMENT, currentFragment?.tag)
        super.onSaveInstanceState(outState)
    }

    override fun onItemClick(videoId: String) {
        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra(VIDEO_EXTRA, videoId)
        startActivity(intent)
    }

    /**
     * BottomNavigationView setup
     */
    private fun setupBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.upcoming_launches -> {
                    currentFragment = UPCOMING.convert(this)
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.container, currentFragment!!)
                        commit()
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.done_launches -> {
                    currentFragment = MAIN.convert(this)
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.container, currentFragment!!)
                        commit()
                    }
                    setCheckedItemOfBottomNavigationView()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.dragons -> {
                    currentFragment = DRAGONS.convert(this)
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.container, currentFragment!!)
                        commit()
                    }
                    setCheckedItemOfBottomNavigationView()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }

    private fun setCheckedItemOfBottomNavigationView() {
        val menu = bottomNavigationView.menu
        val number = when(currentFragment) {
            is MainFragment -> 1
            is DragonsFragment -> 2
            else -> 0
        }
        val menuItem = menu.getItem(number)
        menuItem.isChecked = true
    }
}