package com.github.butul0ve.spacexchecker.adapter

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.butul0ve.spacexchecker.R
import com.github.butul0ve.spacexchecker.adapter.viewholder.PastLaunchesViewHolder
import com.github.butul0ve.spacexchecker.adapter.viewholder.UpcomingLaunchesViewHolder
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.launch_item, null, false)
        return when (viewType) {
            ViewTypes.PAST_LAUNCHES -> PastLaunchesViewHolder(view, clickListener)
            else -> UpcomingLaunchesViewHolder(view, clickListener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val launch = launches[position]

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
        val localDateTime = LocalDateTime.parse(launch.launchDate, formatter)
        val zonedDateTime = localDateTime.atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.systemDefault())

        when (holder.itemViewType) {
            ViewTypes.UPCOMING_LAUNCHES -> {
                holder as UpcomingLaunchesViewHolder
                holder.bind(launch, zonedDateTime)

                val tempDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault())

                val dif = tempDateTime.until(zonedDateTime, ChronoUnit.MILLIS)

                val daysBeforeLaunch = tempDateTime.until(zonedDateTime, ChronoUnit.DAYS)

                if (holder.countDownTimer != null) {
                    holder.countDownTimer!!.cancel()
                }

                if (daysBeforeLaunch < 7) {
                    holder.countDownTimer = object : CountDownTimer(dif, 1000L) {

                        override fun onTick(millisUntilFinished: Long) {
                            val currentDateTime = LocalDateTime
                                    .ofInstant(Instant.ofEpochMilli(millisUntilFinished), ZoneId.of("UTC"))

                            holder.launchDateTextView.text =
                                    holder.itemView.resources.getString(R.string.date_time_before_launch,
                                            daysBeforeLaunch,
                                            currentDateTime.format(DateTimeFormatter
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
                holder.bind(launch, zonedDateTime)
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
}