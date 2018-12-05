package com.example.butul0ve.spacex.adapter

import com.example.butul0ve.spacex.R
import com.example.butul0ve.spacex.db.model.Launch
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * Created by butul0ve on 20.01.18.
 */

class LaunchesAdapter(private val launches: List<Launch>, private val clickListener: LaunchesClickListener) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flight_item, null)
        return when (viewType) {
            ViewTypes.PAST_LAUNCHES -> PastLaunchesViewHolder(view, clickListener)
            else -> UpcomingLaunchesViewHolder(view, clickListener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val launch = launches[position]

        when (holder.itemViewType) {
            ViewTypes.UPCOMING_LAUNCHES -> {
                holder as UpcomingLaunchesViewHolder
                holder.bind(launch)
            }
            ViewTypes.PAST_LAUNCHES -> {
                holder as PastLaunchesViewHolder
                holder.bind(launch)
            }
        }
    }

    override fun getItemCount(): Int {
        return launches.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (launches[position].isLaunchSuccess == null) {
            ViewTypes.UPCOMING_LAUNCHES
        } else {
            ViewTypes.PAST_LAUNCHES
        }
    }

    inner class PastLaunchesViewHolder(itemView: View, private val listener: LaunchesClickListener) :
            RecyclerView.ViewHolder(itemView), View.OnClickListener {

        fun bind(pastLaunch: Launch) {
            itemView.findViewById<TextView>(R.id.rocket_name_text_view).text = pastLaunch.rocket.name
            itemView.findViewById<TextView>(R.id.flight_number_text_view).text = pastLaunch.flightNumber.toString()
            val date = Date(pastLaunch.launchDate.toLong() * 1000)
            val launchDate = SimpleDateFormat("dd MMMM y, HH:mm:ss", Locale.ENGLISH)
            val options = RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .dontTransform()

            Glide.with(itemView.context).load(pastLaunch.links.missionPath)
                    .apply(options)
                    .into(itemView.findViewById<ImageView>(R.id.mission_patch_image_view))
            itemView.findViewById<TextView>(R.id.launch_date_text_view).text = launchDate.format(date)
            itemView.findViewById<TextView>(R.id.detail_text_view).text = pastLaunch.details
            itemView.findViewById<Button>(R.id.article_button).setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(pastLaunch.links.articleLink)
                startActivity(itemView.context, intent, null)
            }
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            listener.onItemClick(layoutPosition)
        }
    }

    inner class UpcomingLaunchesViewHolder(itemView: View, private val listener: LaunchesClickListener) :
            RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onItemClick(layoutPosition)
        }

        fun bind(upcomingLaunch: Launch) {
            itemView.findViewById<TextView>(R.id.rocket_name_text_view).text = upcomingLaunch.rocket.name
            itemView.findViewById<TextView>(R.id.flight_number_text_view).text = upcomingLaunch.flightNumber.toString()

            val date = Date(upcomingLaunch.launchDate.toLong() * 1000)
            val launchDate = SimpleDateFormat("dd MMMM y, HH:mm:ss", Locale.ENGLISH)

            itemView.findViewById<TextView>(R.id.launch_date_text_view).text = launchDate.format(date)
            itemView.findViewById<TextView>(R.id.detail_text_view).text = upcomingLaunch.details
        }
    }
}