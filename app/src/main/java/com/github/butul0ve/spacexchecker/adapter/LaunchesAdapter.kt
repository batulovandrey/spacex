package com.github.butul0ve.spacexchecker.adapter

import android.content.Intent
import android.net.Uri
import android.os.CountDownTimer
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
import com.github.butul0ve.spacexchecker.R
import com.github.butul0ve.spacexchecker.db.model.Launch
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import java.util.*

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

                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
                val localDateTimeOfLaunch = LocalDateTime.parse(launch.launchDate, formatter)
                val dateTimeOfLaunch = localDateTimeOfLaunch.atZone(ZoneId.of("UTC"))
                        .withZoneSameInstant(ZoneId.systemDefault())

                val tempDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault())

                val dif = tempDateTime.until(dateTimeOfLaunch, ChronoUnit.MILLIS)

                val daysBeforeLaunch = tempDateTime.until(dateTimeOfLaunch, ChronoUnit.DAYS)

                if (holder.countDownTimer != null) {
                    holder.countDownTimer!!.cancel()
                }

                if (daysBeforeLaunch < 7) {
                    holder.countDownTimer = object : CountDownTimer(dif, 1000L) {

                        override fun onTick(millisUntilFinished: Long) {
                            val localDateTime = LocalDateTime
                                    .ofInstant(Instant.ofEpochMilli(millisUntilFinished), ZoneId.of("UTC"))

                            holder.launchDateTextView.text =
                                    holder.itemView.resources.getString(R.string.date_time_before_launch,
                                            daysBeforeLaunch,
                                            localDateTime.format(DateTimeFormatter
                                                    .ofPattern("HH:mm:ss")))
                        }

                        override fun onFinish() {
                            holder.launchDateTextView.text = "launch!"
                        }

                    }.start()
                }
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

    fun getLaunchById(position: Int) = launches[position]

    fun updateLaunches(launches: List<Launch>) {
        this.launches as ArrayList
        this.launches.clear()
        this.launches.addAll(launches)
        notifyDataSetChanged()
    }

    inner class PastLaunchesViewHolder(itemView: View, private val listener: LaunchesClickListener) :
            RecyclerView.ViewHolder(itemView), View.OnClickListener {

        fun bind(pastLaunch: Launch) {
            itemView.findViewById<TextView>(R.id.rocket_name_text_view).text = pastLaunch.rocket.name
            itemView.findViewById<TextView>(R.id.flight_number_text_view).text = pastLaunch.flightNumber.toString()

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
            val localDateTime = LocalDateTime.parse(pastLaunch.launchDate, formatter)
            val zonedDateTime = localDateTime.atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(ZoneId.systemDefault())

            val options = RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .dontTransform()

            Glide.with(itemView.context).load(pastLaunch.links.missionPath)
                    .apply(options)
                    .into(itemView.findViewById<ImageView>(R.id.mission_patch_image_view))
            itemView.findViewById<TextView>(R.id.launch_date_text_view).text =
                    zonedDateTime.format(DateTimeFormatter.ofPattern("dd MMMM y, HH:mm:ss"))
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

        var countDownTimer: CountDownTimer? = null
        val launchDateTextView: TextView = itemView.findViewById(R.id.launch_date_text_view)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onItemClick(layoutPosition)
        }

        fun bind(upcomingLaunch: Launch) {
            itemView.findViewById<TextView>(R.id.rocket_name_text_view).text = upcomingLaunch.rocket.name
            itemView.findViewById<TextView>(R.id.flight_number_text_view).text = upcomingLaunch.flightNumber.toString()

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
            val localDateTime = LocalDateTime.parse(upcomingLaunch.launchDate, formatter)
            val zonedDateTime = localDateTime.atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(ZoneId.systemDefault())

            itemView.findViewById<TextView>(R.id.detail_text_view).text = upcomingLaunch.details
            launchDateTextView.text = zonedDateTime.format(DateTimeFormatter.ofPattern("dd MMMM y, HH:mm:ss"))
        }
    }
}