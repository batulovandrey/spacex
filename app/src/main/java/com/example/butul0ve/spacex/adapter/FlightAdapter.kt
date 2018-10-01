package com.example.butul0ve.spacex.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.butul0ve.spacex.R
import com.example.butul0ve.spacex.bean.Flight
import java.text.SimpleDateFormat
import java.util.*
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * Created by butul0ve on 20.01.18.
 */

class FlightAdapter(private val mFlights: List<Flight>, private val mClickListener: FlightClickListener) :
        RecyclerView.Adapter<FlightAdapter.FlightViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flight_item, null)
        return FlightViewHolder(view, mClickListener)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val flight = mFlights[position]
        holder.mRocketNameTextView.text = flight.rocket.name
        holder.mFlightNumberTextView.text = flight.flightNumber.toString()
        val date = Date(flight.launchDate.toLong() * 1000)
        val launchDate = SimpleDateFormat("dd MMMM y, HH:mm:ss", Locale.ENGLISH)
        val options = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.nyan_cat)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .dontTransform()

        Glide.with(holder.itemView.context).load(flight.links.missionPath)
                .apply(options)
                .into(holder.mMissionPatchImageView)

        holder.mLaunchDateTextView.text = launchDate.format(date)
        holder.mDetailTextView.text = flight.details ?: holder.itemView.context.getString(R.string.no_info)

        holder.mArticleButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(flight.links.articleLink)
            startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return mFlights.size
    }

    class FlightViewHolder(itemView: View, private val mClickListener: FlightClickListener) :
            RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var mRocketNameTextView: TextView = itemView.findViewById(R.id.rocket_name_text_view)
        var mFlightNumberTextView: TextView = itemView.findViewById(R.id.flight_number_text_view)
        var mLaunchDateTextView: TextView = itemView.findViewById(R.id.launch_date_text_view)
        var mMissionPatchImageView: ImageView = itemView.findViewById(R.id.mission_patch_image_view)
        var mDetailTextView: TextView = itemView.findViewById(R.id.detail_text_view)
        var mArticleButton: Button = itemView.findViewById(R.id.article_button)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            mClickListener.onItemClick(layoutPosition)
        }
    }
}