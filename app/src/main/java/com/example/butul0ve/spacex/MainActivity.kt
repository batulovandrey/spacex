package com.example.butul0ve.spacex

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.butul0ve.spacex.presenter.MainPresenterImpl

class MainActivity : AppCompatActivity(), MainFragment.OnItemClickListener {

    private var mainFragment: MainFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val manager = supportFragmentManager
        mainFragment = manager.findFragmentById(R.id.container) as MainFragment?

        if (mainFragment == null) {

            mainFragment = MainFragment()
            manager.beginTransaction().apply {
                replace(R.id.container, mainFragment!!)
                commit()
            }

            (mainFragment as MainFragment).setPresenter(MainPresenterImpl())
        }
    }

    override fun onItemClick(videoId: String) {
        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra(VIDEO_EXTRA, videoId)
        startActivity(intent)
    }
}