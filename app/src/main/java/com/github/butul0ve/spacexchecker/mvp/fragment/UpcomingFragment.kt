package com.github.butul0ve.spacexchecker.mvp.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.butul0ve.spacexchecker.SpaceXApp
import com.github.butul0ve.spacexchecker.adapter.LaunchesAdapter
import com.github.butul0ve.spacexchecker.mvp.FragmentInteractor
import com.github.butul0ve.spacexchecker.mvp.interactor.UpcomingMvpInteractor
import com.github.butul0ve.spacexchecker.mvp.presenter.UpcomingPresenter
import com.github.butul0ve.spacexchecker.mvp.view.UpcomingView
import com.github.butul0ve.spacexchecker.ui.BaseFragment
import javax.inject.Inject

class UpcomingFragment : BaseFragment(), UpcomingView, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var clickListener: FragmentInteractor

    @Inject
    lateinit var interactor: UpcomingMvpInteractor

    @InjectPresenter
    lateinit var upcomingPresenter: UpcomingPresenter

    @ProvidePresenter
    fun providePresenter() = UpcomingPresenter(interactor)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        SpaceXApp.netComponent.inject(this)
        try {
            clickListener = activity as FragmentInteractor
        } catch (ex: ClassCastException) {
            throw ClassCastException("${activity.toString()} must implement FragmentInteractor")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout.setOnRefreshListener(this)
        tryAgainButton.setOnClickListener { upcomingPresenter.getData() }
    }

    override fun setAdapter(adapter: LaunchesAdapter) {
        recyclerView.adapter = adapter
    }

    override fun showProgressBar() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideProgressBar() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onRefresh() {
        upcomingPresenter.getData()
    }

    override fun showTryAgainButton() {
        tryAgainButton.visibility = View.VISIBLE
    }

    override fun hideTryAgainButton() {
        tryAgainButton.visibility = View.GONE
    }

    override fun onYoutubeButtonClick(position: Int) {
        upcomingPresenter.openYoutubePlayerActivity(position)
    }

    override fun onRedditCampaingButtonClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRedditLaunchButtonClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openYoutube(link: String) {
        clickListener.showVideo(link)
    }
}