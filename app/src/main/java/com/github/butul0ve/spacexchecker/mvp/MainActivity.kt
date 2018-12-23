package com.github.butul0ve.spacexchecker.mvp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.github.butul0ve.spacexchecker.PlayerActivity
import com.github.butul0ve.spacexchecker.R
import com.github.butul0ve.spacexchecker.VIDEO_EXTRA
import com.github.butul0ve.spacexchecker.adapter.SpaceXPagerAdapter
import com.github.butul0ve.spacexchecker.mvp.fragment.DragonsFragment
import com.github.butul0ve.spacexchecker.mvp.fragment.MainFragment
import com.github.butul0ve.spacexchecker.mvp.fragment.RocketsFragment
import com.github.butul0ve.spacexchecker.mvp.fragment.UpcomingFragment
import com.github.butul0ve.spacexchecker.mvp.presenter.MainActivityPresenter
import com.github.butul0ve.spacexchecker.mvp.view.MainActivityView
import com.github.butul0ve.spacexchecker.ui.BaseFragment
import com.github.butul0ve.spacexchecker.ui.MvpAppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber

class MainActivity : MvpAppCompatActivity(), MainActivityView {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var placeHolderIV: ImageView
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: PagerAdapter

    @InjectPresenter
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
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

    override fun showVideo(videoId: String) {
        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra(VIDEO_EXTRA, videoId)
        startActivity(intent)
    }

    override fun openReddit(link: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(link)
        startActivity(intent)
    }

    override fun showFragment(fragment: BaseFragment) {
        viewPager.currentItem = when(fragment) {
            is UpcomingFragment -> 0
            is MainFragment -> 1
            is DragonsFragment -> 2
            is RocketsFragment -> 3
            else -> 1
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
                    presenter.openRocketsFragment()
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