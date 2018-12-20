package com.github.butul0ve.spacexchecker.mvp.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.butul0ve.spacexchecker.SpaceXApp
import com.github.butul0ve.spacexchecker.adapter.DragonAdapter
import com.github.butul0ve.spacexchecker.mvp.interactor.DragonsMvpInteractor
import com.github.butul0ve.spacexchecker.mvp.presenter.DragonsPresenter
import com.github.butul0ve.spacexchecker.mvp.view.DragonsView
import com.github.butul0ve.spacexchecker.ui.BaseFragment
import javax.inject.Inject

class DragonsFragment : BaseFragment(), DragonsView, SwipeRefreshLayout.OnRefreshListener {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout.setOnRefreshListener(this)
        tryAgainButton.setOnClickListener { dragonsPresenter.getData() }
    }

    override fun setAdapter(adapter: DragonAdapter) {
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
        dragonsPresenter.getData()
    }
}