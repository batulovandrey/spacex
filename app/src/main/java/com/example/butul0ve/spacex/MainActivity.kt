package com.example.butul0ve.spacex

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import com.example.butul0ve.spacex.adapter.FlightAdapter
import com.example.butul0ve.spacex.presenter.MainPresenter
import com.example.butul0ve.spacex.presenter.MainPresenterImpl
import com.example.butul0ve.spacex.view.MainView

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mMainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById(R.id.recycler_view)
        mProgressBar = findViewById(R.id.progress_bar)
        mMainPresenter = MainPresenterImpl(this)
        checkWritePermission()
    }

    private fun checkWritePermission() {
        if (isPermissionGranted(WRITE_EXTERNAL_STORAGE)) {
            mMainPresenter.getData()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(Array(1) { WRITE_EXTERNAL_STORAGE }, MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    private fun isPermissionGranted(permission: String): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(this, permission)
        return permissionCheck == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                    mMainPresenter.getData()
                }
            }
        }
    }

    override fun showProgressBar() {
        runOnUiThread { mProgressBar.visibility = View.VISIBLE }
    }

    override fun hideProgressBar() {
        runOnUiThread { mProgressBar.visibility = View.GONE }
    }

    override fun openYouTube(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    override fun setAdapter(adapter: FlightAdapter) {
        runOnUiThread { mRecyclerView.adapter = adapter }
    }

    companion object {

        private val MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 7
    }
}