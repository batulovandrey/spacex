package com.github.butul0ve.spacexchecker.adapter.viewholder

import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.butul0ve.spacexchecker.R
import com.github.butul0ve.spacexchecker.db.model.Dragon
import com.squareup.picasso.Picasso

class DragonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(dragon: Dragon) {
        itemView.findViewById<TextView>(R.id.dragon_name_text_view).text = dragon.name
        itemView.findViewById<TextView>(R.id.dragon_type_text_view).text = dragon.type
        itemView.findViewById<TextView>(R.id.dragon_active_text_view).text = dragon.isActive.toString()
        itemView.findViewById<TextView>(R.id.dragon_description_text_view).text = dragon.description
        itemView.findViewById<TextView>(R.id.first_flight_text_view).text = dragon.firstFlight
        itemView.findViewById<ImageButton>(R.id.open_wiki_button).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(dragon.wiki)
            ContextCompat.startActivity(itemView.context, intent, null)
        }

        if (dragon.images.isNotEmpty()) {
            val tableLayout = TableLayout(itemView.context)
            tableLayout.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)

            dragon.images.forEach {
                val iv = ImageView(itemView.context)

                iv.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)

                Picasso.get()
                        .load(it)
                        .into(iv)

                tableLayout.addView(iv)
            }
            itemView.findViewById<LinearLayout>(R.id.images_layout).addView(tableLayout)
        }
    }
}