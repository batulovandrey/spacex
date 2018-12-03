package com.example.butul0ve.spacex.mvp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.butul0ve.spacex.PlayerActivity
import com.example.butul0ve.spacex.R
import com.example.butul0ve.spacex.SpaceXApp
import com.example.butul0ve.spacex.VIDEO_EXTRA
import com.example.butul0ve.spacex.adapter.SpaceXPagerAdapter
import com.example.butul0ve.spacex.db.DataManager
import com.example.butul0ve.spacex.mvp.fragment.DragonsFragment
import com.example.butul0ve.spacex.mvp.fragment.MainFragment
import com.example.butul0ve.spacex.mvp.presenter.MainActivityPresenter
import com.example.butul0ve.spacex.mvp.view.MainActivityView
import com.example.butul0ve.spacex.ui.BaseFragment
import com.example.butul0ve.spacex.ui.MvpAppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainFragment.OnItemClickListener, MainActivityView {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var placeHolderIV: ImageView
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: PagerAdapter

    @Inject
    lateinit var dataManager: DataManager

    @InjectPresenter
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        SpaceXApp.netComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeHolderIV = findViewById(R.id.placeholder_iv)
        viewPager = findViewById(R.id.view_pager)
        pagerAdapter = SpaceXPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
                Timber.d("onPageScrollStateChanged $state")
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                Timber.d("onPageScrolled $position")
            }

            override fun onPageSelected(position: Int) {
                Timber.d("onPageSelected $position")
                setCheckedItemOfBottomNavigationView(position)
            }
        })

        setupBottomNavigationView()
    }

    override fun onItemClick(videoId: String) {
        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra(VIDEO_EXTRA, videoId)
        startActivity(intent)
    }

    override fun showFragment(fragment: BaseFragment) {
        viewPager.currentItem = when(fragment) {
            is MainFragment -> 1
            is DragonsFragment -> 2
            else -> 0
        }
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

    private fun setCheckedItemOfBottomNavigationView(position: Int) {
        val menu = bottomNavigationView.menu
        val menuItem = menu.getItem(position)
        menuItem.isChecked = true
    }
}