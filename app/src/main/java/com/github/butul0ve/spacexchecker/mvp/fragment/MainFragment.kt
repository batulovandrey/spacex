package com.github.butul0ve.spacexchecker.mvp.fragment

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.butul0ve.spacexchecker.R
import com.github.butul0ve.spacexchecker.SpaceXApp
import com.github.butul0ve.spacexchecker.adapter.LaunchesAdapter
import com.github.butul0ve.spacexchecker.mvp.FragmentInteractor
import com.github.butul0ve.spacexchecker.mvp.interactor.MainMvpInteractor
import com.github.butul0ve.spacexchecker.mvp.presenter.MainPresenter
import com.github.butul0ve.spacexchecker.mvp.view.MainView
import com.github.butul0ve.spacexchecker.ui.BaseFragment
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class MainFragment : BaseFragment(), MainView, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var clickListener: FragmentInteractor

    @Inject
    lateinit var interactor: MainMvpInteractor

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun provideMainPresenter() = MainPresenter(interactor)

    override fun onAttach(context: Context) {
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
        tryAgainButton.setOnClickListener {
            mainPresenter.getNextLaunch()
            mainPresenter.getData()
        }
    }

    override fun showProgressBar() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideProgressBar() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun openYouTube(link: String) {
        clickListener.showVideo(link)
    }

    override fun setAdapter(adapter: LaunchesAdapter) {
        recyclerView.adapter = adapter
    }

    override fun showToast(message: Int) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun showButtonTryAgain() {
        tryAgainButton.visibility = View.VISIBLE
    }

    override fun hideButtonTryAgain() {
        tryAgainButton.visibility = View.GONE
    }

    override fun showNextLaunch(text: String) {
        activity?.let {
            val snack = Snackbar.make(it.findViewById(android.R.id.content),
                    text,
                    7000)
            val tv = snack.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            tv.setBackgroundColor(ContextCompat.getColor(it, R.color.white))
            tv.setTextColor(ContextCompat.getColor(it, R.color.black))
            tv.setTypeface(tv.typeface, Typeface.BOLD)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tv.textAlignment = View.TEXT_ALIGNMENT_CENTER
            } else {
                tv.gravity = Gravity.CENTER_HORIZONTAL
            }
            snack.show()
        }
    }

    override fun onYoutubeButtonClick(position: Int) {
        mainPresenter.openYoutubePlayerActivity(position)
    }

    override fun onRedditCampaignButtonClick(position: Int) {
        mainPresenter.openRedditCampaing(position)
    }

    override fun onRedditLaunchButtonClick(position: Int) {
        mainPresenter.openRedditLaunch(position)
    }

    override fun openReddit(link: String) {
        clickListener.openReddit(link)
    }

    override fun onRefresh() {
        mainPresenter.getData()
    }
}