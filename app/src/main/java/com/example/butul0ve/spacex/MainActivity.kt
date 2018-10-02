package com.example.butul0ve.spacex

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.butul0ve.spacex.presenter.MainPresenterImpl
import com.example.butul0ve.spacex.utils.MAIN
import com.example.butul0ve.spacex.utils.DRAGONS
import com.example.butul0ve.spacex.utils.convert
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val CURRENT_FRAGMENT = "current_fragment"

class MainActivity : AppCompatActivity(), MainFragment.OnItemClickListener {

    private val TAG = MainActivity::class.java.simpleName

    private var currentFragment: Fragment? = null
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigationView()
        val manager = supportFragmentManager

        if (savedInstanceState == null) {
            currentFragment = MainFragment()

            manager.beginTransaction().apply {
                replace(R.id.container, currentFragment as MainFragment)
                commit()
            }

            (currentFragment as MainFragment).setPresenter(MainPresenterImpl())
        } else {
            currentFragment = manager.findFragmentById(R.id.container)
            if (currentFragment == null) {
                currentFragment = savedInstanceState.getString(CURRENT_FRAGMENT, MAIN).convert()
                manager.beginTransaction().apply {
                    replace(R.id.container, currentFragment!!)
                    commit()
                }
            }
        }

        setCheckedItemOfBottomNavigationView()
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
                R.id.todo_launches -> {

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.done_launches -> {
                    currentFragment = MAIN.convert()
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.container, currentFragment!!)
                        commit()
                    }
                    setCheckedItemOfBottomNavigationView()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.rockets -> {
                    currentFragment = DRAGONS.convert()
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