package com.example.butul0ve.spacex.mvp.fragment

import android.content.Context
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.butul0ve.spacex.R
import com.example.butul0ve.spacex.SpaceXApp
import com.example.butul0ve.spacex.adapter.PastLaunchesAdapter
import com.example.butul0ve.spacex.db.DataManager
import com.example.butul0ve.spacex.mvp.view.MainView
import com.example.butul0ve.spacex.mvp.presenter.MainPresenter
import com.example.butul0ve.spacex.ui.BaseFragment
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class MainFragment : BaseFragment(), MainView {

    private lateinit var clickListener: OnItemClickListener
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tryAgainButton: Button

    @Inject
    lateinit var dataManager: DataManager

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun provideMainPresenter() = MainPresenter(dataManager)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        SpaceXApp.netComponent.inject(this)
        try {
            clickListener = activity as OnItemClickListener
        } catch (ex: ClassCastException) {
            throw ClassCastException("${activity.toString()} must implement OnItemClickListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = layoutInflater.inflate(R.layout.main_fragment, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)
        tryAgainButton = view.findViewById(R.id.try_again_button)
        tryAgainButton.setOnClickListener { mainPresenter.getData() }
        return view
    }

    override fun showProgressBar() {
        activity?.runOnUiThread { progressBar.visibility = View.VISIBLE }
    }

    override fun hideProgressBar() {
        activity?.runOnUiThread { progressBar.visibility = View.GONE }
    }

    override fun openYouTube(uri: Uri) {
        clickListener.onItemClick(uri.query.substringAfter("="))
    }

    override fun setAdapter(adapter: PastLaunchesAdapter) {
        activity?.runOnUiThread { recyclerView.adapter = adapter }
    }

    override fun showToast(message: Int) {
        activity?.runOnUiThread { Toast.makeText(activity, message, Toast.LENGTH_LONG).show() }
    }

    override fun showButtonTryAgain() {
        activity?.runOnUiThread { tryAgainButton.visibility = View.VISIBLE }
    }

    override fun hideButtonTryAgain() {
        activity?.runOnUiThread { tryAgainButton.visibility = View.GONE }
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

    override fun onItemClick(position: Int) {
        mainPresenter.onItemClick(position)
    }

    interface OnItemClickListener {

        fun onItemClick(videoId: String)
    }
}