package com.example.butul0ve.spacex

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.example.butul0ve.spacex.adapter.FlightAdapter
import com.example.butul0ve.spacex.presenter.MainPresenter
import com.example.butul0ve.spacex.presenter.MainPresenterImpl
import com.example.butul0ve.spacex.view.MainView

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mTryAgainButton: Button
    private lateinit var mMainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mMainPresenter = MainPresenterImpl(this)

        mRecyclerView = findViewById(R.id.recycler_view)
        mProgressBar = findViewById(R.id.progress_bar)
        mTryAgainButton = findViewById(R.id.try_again_button)
        mTryAgainButton.setOnClickListener { mMainPresenter.getData() }

        mMainPresenter.getData()
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

    override fun showToast(message: Int) {
        runOnUiThread { Toast.makeText(this, message, Toast.LENGTH_LONG).show() }
    }

    override fun showButtonTryAgain() {
        runOnUiThread { mTryAgainButton.visibility = View.VISIBLE }
    }

    override fun hideButtonTryAgain() {
        runOnUiThread { mTryAgainButton.visibility = View.GONE }
    }
}