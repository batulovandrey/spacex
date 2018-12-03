package com.example.butul0ve.spacex.mvp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.butul0ve.spacex.PlayerActivity
import com.example.butul0ve.spacex.R
import com.example.butul0ve.spacex.SpaceXApp
import com.example.butul0ve.spacex.VIDEO_EXTRA
import com.example.butul0ve.spacex.db.DataManager
import com.example.butul0ve.spacex.mvp.fragment.DragonsFragment
import com.example.butul0ve.spacex.mvp.fragment.MainFragment
import com.example.butul0ve.spacex.mvp.presenter.MainActivityPresenter
import com.example.butul0ve.spacex.mvp.view.MainActivityView
import com.example.butul0ve.spacex.ui.BaseFragment
import com.example.butul0ve.spacex.ui.MvpAppCompatActivity
import com.example.butul0ve.spacex.utils.convert
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

private const val CURRENT_FRAGMENT = "current_fragment"

class MainActivity : MvpAppCompatActivity(), MainFragment.OnItemClickListener, MainActivityView {

    private var currentFragment: Fragment? = null
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var placeHolderIV: ImageView

    @Inject
    lateinit var dataManager: DataManager

    @InjectPresenter
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        SpaceXApp.netComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeHolderIV = findViewById(R.id.placeholder_iv)

        setupBottomNavigationView()
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

    override fun showFragment(fragment: BaseFragment) {
        currentFragment = fragment::class.java.simpleName.convert()
        val manager = supportFragmentManager

        manager.beginTransaction().apply {
            replace(R.id.container, currentFragment as BaseFragment)
            commit()
        }

        setCheckedItemOfBottomNavigationView()
    }

    /**
     * BottomNavigationView setup
     */
    private fun setupBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.upcoming_launches -> {
                    presenter.openUpcomingFragment()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.done_launches -> {
                    presenter.openMainFragment()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.dragons -> {
                    presenter.openDragonsFragment()
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
        val number = when (currentFragment) {
            is MainFragment -> 1
            is DragonsFragment -> 2
            else -> 0
        }
        val menuItem = menu.getItem(number)
        menuItem.isChecked = true
    }
}