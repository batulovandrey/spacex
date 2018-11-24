package com.example.butul0ve.spacex

import android.content.Context
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.butul0ve.spacex.adapter.PastLaunchesAdapter
import com.example.butul0ve.spacex.presenter.MainPresenter
import com.example.butul0ve.spacex.utils.isConnectedToInternet
import com.example.butul0ve.spacex.view.MainView
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment(), MainView {

    private lateinit var clickListener: OnItemClickListener
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tryAgainButton: Button
    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
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
//        tryAgainButton.setOnClickListener { mainPresenter.getData() }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isConnected = activity?.isConnectedToInternet()
        isConnected?.let { mainPresenter.getData(it) }
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

    fun setPresenter(presenter: MainPresenter) {
        mainPresenter = presenter
        mainPresenter.attachView(this)
    }

    fun getPresenter() = mainPresenter

    interface OnItemClickListener {

        fun onItemClick(videoId: String)
    }
}