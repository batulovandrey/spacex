package com.github.butul0ve.spacexchecker.adapter.viewholder

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.butul0ve.spacexchecker.R
import com.github.butul0ve.spacexchecker.adapter.LaunchesClickListener
import com.github.butul0ve.spacexchecker.db.model.Launch
import com.squareup.picasso.Picasso
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

class PastLaunchesViewHolder(itemView: View, private val listener: LaunchesClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        /*
            onClick item not used yet
         */
    }

    fun bind(pastLaunch: Launch, dateTime: ZonedDateTime) {
        itemView.findViewById<TextView>(R.id.rocket_name_text_view).text = pastLaunch.rocket.name
        itemView.findViewById<TextView>(R.id.flight_number_text_view).text = pastLaunch.flightNumber.toString()
        itemView.findViewById<TextView>(R.id.mission_name_text_view).text = pastLaunch.missionName

        Picasso.get()
                .load(pastLaunch.links.missionPathSmall)
                .into(itemView.findViewById<ImageView>(R.id.mission_patch_image_view))

        itemView.findViewById<TextView>(R.id.launch_date_text_view).text =
                dateTime.format(DateTimeFormatter.ofPattern("dd MMMM y, HH:mm:ss"))
        itemView.findViewById<TextView>(R.id.detail_text_view).text = pastLaunch.details

        val articleButton = itemView.findViewById<ImageButton>(R.id.article_button)
        if (pastLaunch.links.articleLink.isNullOrEmpty()) {
            articleButton.visibility = View.GONE
        } else {
            articleButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(pastLaunch.links.articleLink)
                ContextCompat.startActivity(itemView.context, intent, null)
            }
        }

        val youtubeButton = itemView.findViewById<ImageButton>(R.id.youtube_button)
        if (pastLaunch.links.videoLink.isNullOrEmpty()) {
            youtubeButton.visibility = View.GONE
        } else {
            youtubeButton.setOnClickListener {
                listener.onYoutubeButtonClick(layoutPosition)
            }
        }

        val redditCampaignButton = itemView.findViewById<ImageButton>(R.id.reddit_campaign_button)
        if (pastLaunch.links.redditCampaign.isNullOrEmpty()) {
            redditCampaignButton.visibility = View.GONE
        } else {
            redditCampaignButton.setOnClickListener {
                listener.onRedditCampaignButtonClick(layoutPosition)
            }
        }

        val redditLaunchButton = itemView.findViewById<ImageButton>(R.id.reddit_launch_button)
        if (pastLaunch.links.redditLaunch.isNullOrEmpty()) {
            redditLaunchButton.visibility = View.GONE
        } else {
            redditLaunchButton.setOnClickListener {
                listener.onRedditLaunchButtonClick(layoutPosition)
            }
        }
    }
}