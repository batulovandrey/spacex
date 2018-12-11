package com.example.butul0ve.spacex.mvp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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

class DragonsFragment : BaseFragment(), RocketsView, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var dragonsRecycler: RecyclerView
    private lateinit var tryAgainButton: Button
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

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
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener(this)
        tryAgainButton = view.findViewById(R.id.try_again_button)
        tryAgainButton.setOnClickListener { dragonsPresenter.getData() }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark)
    }

    override fun setAdapter(adapter: DragonAdapter) {
        activity?.runOnUiThread { dragonsRecycler.adapter = adapter }
    }

    override fun showProgressBar() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideProgressBar() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showButtonTryAgain() {
        tryAgainButton.visibility = View.VISIBLE
    }

    override fun hideButtonTryAgain() {
        tryAgainButton.visibility = View.GONE
    }

    override fun onRefresh() {
        dragonsPresenter.getData()
    }
}