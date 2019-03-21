package com.github.butul0ve.spacexchecker.adapter.viewholder

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.butul0ve.spacexchecker.R
import com.github.butul0ve.spacexchecker.db.model.Rocket

class RocketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(rocket: Rocket) {
        itemView.findViewById<TextView>(R.id.rocket_name_text_view).text = rocket.name
        itemView.findViewById<TextView>(R.id.rocket_is_active_text_view).text = rocket.isActive.toString()
        itemView.findViewById<TextView>(R.id.first_flight_text_view).text = rocket.firstFlight
        itemView.findViewById<TextView>(R.id.rocket_stages_text_view).text = rocket.stages.toString()

        rocket.height?.let {
            itemView.findViewById<TextView>(R.id.rocket_height_text_view).text =
                    itemView.resources.getString(R.string.meters_feet,
                            rocket.height?.meters,
                            rocket.height?.feet)
        }

        rocket.diameter?.let {
            itemView.findViewById<TextView>(R.id.rocket_diameter_text_view).text =
                    itemView.resources.getString(R.string.meters_feet,
                            rocket.diameter?.meters,
                            rocket.diameter?.feet)
        }

        rocket.mass?.let {
            itemView.findViewById<TextView>(R.id.rocket_mass_text_view).text =
                    itemView.resources.getString(R.string.kg_lb,
                            rocket.mass?.kg,
                            rocket.mass?.lb)
        }

        itemView.findViewById<TextView>(R.id.rocket_description_text_view).text = rocket.description
        itemView.findViewById<ImageButton>(R.id.article_button).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(rocket.wiki)
            ContextCompat.startActivity(itemView.context, intent, null)
        }
    }
}