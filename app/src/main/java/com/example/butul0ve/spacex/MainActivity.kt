package com.example.butul0ve.spacex

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.butul0ve.spacex.presenter.MainPresenterImpl

class MainActivity : AppCompatActivity() {

    private var mainFragment: MainFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val manager = supportFragmentManager
        mainFragment = manager.findFragmentById(R.id.container) as MainFragment?

        if (mainFragment == null) {

            mainFragment = MainFragment()
            manager.beginTransaction().apply {
                replace(R.id.container, mainFragment)
                commit()
            }

            (mainFragment as MainFragment).setPresenter(MainPresenterImpl())
        }
    }
}