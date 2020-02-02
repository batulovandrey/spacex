package com.github.butul0ve.spacexchecker.mvp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.github.butul0ve.spacexchecker.PlayerActivity
import com.github.butul0ve.spacexchecker.R
import com.github.butul0ve.spacexchecker.VIDEO_EXTRA
import com.github.butul0ve.spacexchecker.databinding.ActivityMainBinding
import com.github.butul0ve.spacexchecker.utils.setupWithNavController
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import timber.log.Timber

class MainActivity: AppCompatActivity(), FragmentInteractor {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT <= 19) {
            try {
                ProviderInstaller.installIfNeeded(this)
            } catch (ex: GooglePlayServicesRepairableException) {
                Timber.e(ex)
                GoogleApiAvailability.getInstance()
                        .showErrorNotification(this, ex.connectionStatusCode)
            }
        }

        val bind = DataBindingUtil
                .setContentView<ActivityMainBinding>(
                        this,
                        R.layout.activity_main
                )

        setupNavigate(bind)
    }

    private fun setupNavigate(bind: ActivityMainBinding) {
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
        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra(VIDEO_EXTRA, videoId)
        startActivity(intent)
    }

    override fun openReddit(link: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(link)
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            Timber.e(ex)
        }
    }
}