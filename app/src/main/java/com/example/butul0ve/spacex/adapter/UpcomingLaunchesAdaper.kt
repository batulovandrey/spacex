package com.example.butul0ve.spacex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.butul0ve.spacex.R
import com.example.butul0ve.spacex.bean.UpcomingLaunch
import java.text.SimpleDateFormat
import java.util.*

class UpcomingLaunchesAdaper(private val upcomingLaunches: List<UpcomingLaunch>): RecyclerView.Adapter<UpcomingLaunchesAdaper.UpcomingLaunchesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingLaunchesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flight_item, null)
        return UpcomingLaunchesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return upcomingLaunches.size
    }

    override fun onBindViewHolder(holder: UpcomingLaunchesViewHolder, position: Int) {
        val launch = upcomingLaunches[position]
        holder.bind(launch)
    }

    class UpcomingLaunchesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(upcomingLaunch: UpcomingLaunch) {
            itemView.findViewById<TextView>(R.id.rocket_name_text_view).text = upcomingLaunch.rocket.name
            itemView.findViewById<TextView>(R.id.flight_number_text_view).text = upcomingLaunch.flightNumber.toString()

            val date = Date(upcomingLaunch.launchDate.toLong() * 1000)
            val launchDate = SimpleDateFormat("dd MMMM y, HH:mm:ss", Locale.ENGLISH)

            itemView.findViewById<TextView>(R.id.launch_date_text_view).text = launchDate.format(date)
            itemView.findViewById<TextView>(R.id.detail_text_view).text = upcomingLaunch.details
        }
    }
}