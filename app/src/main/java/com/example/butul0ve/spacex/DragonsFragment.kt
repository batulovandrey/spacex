package com.example.butul0ve.spacex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.butul0ve.spacex.adapter.DragonAdapter
import com.example.butul0ve.spacex.presenter.DragonsPresenter
import com.example.butul0ve.spacex.view.RocketsView

class DragonsFragment: Fragment(), RocketsView {

    private lateinit var dragonsPresenter: DragonsPresenter
    private lateinit var dragonsRecycler: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tryAgainButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dragons_fragment, container, false)
        dragonsRecycler = view.findViewById(R.id.dragons_recycler)
        progressBar = view.findViewById(R.id.progress_bar)
        tryAgainButton = view.findViewById(R.id.try_again_button)
        tryAgainButton.setOnClickListener { dragonsPresenter.getData() }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dragonsPresenter.getData()
    }

    fun setPresenter(presenter: DragonsPresenter) {
        dragonsPresenter = presenter
        dragonsPresenter.attachView(this)
    }

    override fun setAdapter(adapter: DragonAdapter) {
        activity?.runOnUiThread { dragonsRecycler.adapter = adapter }
    }

    override fun showProgressBar() {
        activity?.runOnUiThread { progressBar.visibility = View.VISIBLE }
    }

    override fun hideProgressBar() {
        activity?.runOnUiThread { progressBar.visibility = View.GONE }
    }
}