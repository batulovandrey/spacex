package com.example.butul0ve.spacex


import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.example.butul0ve.spacex.adapter.FlightAdapter
import com.example.butul0ve.spacex.bean.Flight


class MainActivity : AppCompatActivity(), ExecutionThread.CustomCallback {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById(R.id.recycler_view)
        mProgressBar = findViewById(R.id.progress_bar)
        checkWritePermission()
    }

    private fun checkWritePermission() {
        if (isPermissionGranted(WRITE_EXTERNAL_STORAGE)) {
            ExecutionThread(this).start()
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
                    ExecutionThread(this).start()
                }
            }
        }
    }

    override fun setFlights(flights: List<Flight>?) {
        runOnUiThread { mRecyclerView.adapter = FlightAdapter(flights!!) }
    }

    override fun showProgressBar() {
        runOnUiThread { mProgressBar.visibility = View.VISIBLE }
    }

    override fun hideProgressBar() {
        runOnUiThread { mProgressBar.visibility = View.GONE }
    }

    companion object {

        private val MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 7
    }
}