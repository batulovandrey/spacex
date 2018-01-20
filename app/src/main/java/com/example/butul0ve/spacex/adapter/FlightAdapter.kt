package com.example.butul0ve.spacex.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.butul0ve.spacex.R
import com.example.butul0ve.spacex.bean.Flight

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
        holder.setDetailText(flight.details)
    }

    override fun getItemCount(): Int {
        return mFlights.size
    }

    class FlightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var mDetailTextView: TextView = itemView.findViewById(R.id.detail_text_view)

        fun setDetailText(text: String) {
            mDetailTextView.text = text
        }
    }
}