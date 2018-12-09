package com.example.butul0ve.spacex.mvp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.butul0ve.spacex.R
import com.example.butul0ve.spacex.SpaceXApp
import com.example.butul0ve.spacex.adapter.DragonAdapter
import com.example.butul0ve.spacex.mvp.interactor.DragonsMvpInteractor
import com.example.butul0ve.spacex.mvp.presenter.DragonsPresenter
import com.example.butul0ve.spacex.mvp.view.RocketsView
import com.example.butul0ve.spacex.ui.BaseFragment
import javax.inject.Inject

class DragonsFragment : BaseFragment(), RocketsView {

    private lateinit var dragonsRecycler: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tryAgainButton: Button

    @Inject
    lateinit var interactor: DragonsMvpInteractor

    @InjectPresenter
    lateinit var dragonsPresenter: DragonsPresenter

    @ProvidePresenter
    fun providePresenter() = DragonsPresenter(interactor)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        SpaceXApp.netComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dragons_fragment, container, false)
        dragonsRecycler = view.findViewById(R.id.dragons_recycler)
        progressBar = view.findViewById(R.id.progress_bar)
        tryAgainButton = view.findViewById(R.id.try_again_button)
        tryAgainButton.setOnClickListener { dragonsPresenter.getData() }
        return view
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