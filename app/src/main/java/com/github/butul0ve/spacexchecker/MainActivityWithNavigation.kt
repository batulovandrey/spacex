package com.github.butul0ve.spacexchecker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.github.butul0ve.spacexchecker.databinding.ActivityMainWithNavigationBinding
import com.github.butul0ve.spacexchecker.mvp.FragmentInteractor
import com.github.butul0ve.spacexchecker.utils.setupWithNavController

class MainActivityWithNavigation: AppCompatActivity(), FragmentInteractor {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bind = DataBindingUtil
                .setContentView<ActivityMainWithNavigationBinding>(
                        this,
                        R.layout.activity_main_with_navigation
                )

        setupNavigate(bind)
    }

    private fun setupNavigate(bind: ActivityMainWithNavigationBinding) {
        val controller = bind.bottomNavigationView.setupWithNavController(
                listOf(
                        R.navigation.nav_tab1,
                        R.navigation.nav_tab2,
                        R.navigation.nav_tab3,
                        R.navigation.nav_tab4
                ),
                supportFragmentManager,
                R.id.nav_host_fragment,
                Intent()
        )

        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })

        currentNavController = controller
    }


    override fun onNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun showVideo(videoId: String) {

    }

    override fun openReddit(link: String) {

    }
}