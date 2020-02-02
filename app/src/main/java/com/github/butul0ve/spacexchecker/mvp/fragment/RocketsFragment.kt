package com.github.butul0ve.spacexchecker.mvp.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.butul0ve.spacexchecker.SpaceXApp
import com.github.butul0ve.spacexchecker.adapter.RocketAdapter
import com.github.butul0ve.spacexchecker.mvp.interactor.RocketsMvpInteractor
import com.github.butul0ve.spacexchecker.mvp.presenter.RocketsPresenter
import com.github.butul0ve.spacexchecker.mvp.view.RocketsView
import com.github.butul0ve.spacexchecker.ui.BaseFragment
import javax.inject.Inject

class RocketsFragment: BaseFragment(), RocketsView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var intercator: RocketsMvpInteractor

    @InjectPresenter
    lateinit var rocketsPresenter: RocketsPresenter

    @ProvidePresenter
    fun providePresenter() = RocketsPresenter(intercator)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        SpaceXApp.netComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout.setOnRefreshListener(this)
        tryAgainButton.setOnClickListener { rocketsPresenter.getData() }
    }

    override fun setAdapter(adapter: RocketAdapter) {
        recyclerView.adapter = adapter
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
        rocketsPresenter.getData()
    }
}