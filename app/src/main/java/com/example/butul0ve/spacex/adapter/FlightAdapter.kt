package com.example.butul0ve.spacex.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.butul0ve.spacex.R
import com.example.butul0ve.spacex.bean.Flight
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by butul0ve on 20.01.18.
 */

class FlightAdapter(private val mFlights: List<Flight>) : RecyclerView.Adapter<FlightAdapter.FlightViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flight_item, null)
        return FlightViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val flight = mFlights[position]
        holder.mRocketNameTextView.text = flight.rocket.name
        val date = Date(flight.launchDate.toLong() * 1000)
        val launchDate = SimpleDateFormat("dd MMMM y, HH:mm:ss", Locale.ENGLISH)
        Picasso.with(holder.itemView.context)
                .load(flight.links.missionPath)
                .placeholder(R.drawable.nyan_cat)
                .into(holder.mMissionPatchImageView)
        holder.mLaunchDateTextView.text = launchDate.format(date)
        holder.mDetailTextView.text = flight.details
    }

    override fun getItemCount(): Int {
        return mFlights.size
    }

    class FlightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mRocketNameTextView: TextView = itemView.findViewById(R.id.rocket_name_text_view)
        var mLaunchDateTextView: TextView = itemView.findViewById(R.id.launch_date_text_view)
        var mMissionPatchImageView: ImageView = itemView.findViewById(R.id.mission_patch_image_view)
        var mDetailTextView: TextView = itemView.findViewById(R.id.detail_text_view)
    }
}