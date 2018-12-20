package com.github.butul0ve.spacexchecker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.butul0ve.spacexchecker.R


open class BaseFragment: MvpAppCompatFragment() {

    protected lateinit var recyclerView: RecyclerView
    protected lateinit var tryAgainButton: Button
    protected lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.base_fragment, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        tryAgainButton = view.findViewById(R.id.try_again_button)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark)
    }
}