package com.github.butul0ve.spacexchecker.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.butul0ve.spacexchecker.R
import com.github.butul0ve.spacexchecker.db.model.Dragon

class DragonAdapter(private val dragons: List<Dragon>): RecyclerView.Adapter<DragonAdapter.DragonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DragonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dragon_item, null)
        return DragonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dragons.size
    }

    override fun onBindViewHolder(holder: DragonViewHolder, position: Int) {
        val dragon = dragons[position]
        holder.bind(dragon)
    }

    class DragonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(dragon: Dragon) {
            itemView.findViewById<TextView>(R.id.dragon_name_text_view).text = dragon.name
            itemView.findViewById<TextView>(R.id.dragon_type_text_view).text = dragon.type
            itemView.findViewById<TextView>(R.id.dragon_active_text_view).text = dragon.isActive.toString()
            itemView.findViewById<TextView>(R.id.dragon_description_text_view).text = dragon.description
            itemView.findViewById<TextView>(R.id.first_flight_text_view).text = dragon.firstFlight
            itemView.findViewById<Button>(R.id.open_wiki_button).setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(dragon.wiki)
                startActivity(itemView.context, intent, null)
            }

            if (dragon.images.isNotEmpty()) {
                val tableLayout = TableLayout(itemView.context)
                dragon.images.forEach {
                    val iv = ImageView(itemView.context)
                    Glide.with(itemView.context)
                            .load(it)
                            .into(iv)
                    tableLayout.addView(iv)
                }
                itemView.findViewById<LinearLayout>(R.id.images_layout).addView(tableLayout)
            }
        }
    }
}