package com.github.butul0ve.spacexchecker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.butul0ve.spacexchecker.R
import com.github.butul0ve.spacexchecker.adapter.viewholder.DragonViewHolder
import com.github.butul0ve.spacexchecker.db.model.Dragon
import com.squareup.picasso.Picasso

class DragonAdapter(
        private val dragons: List<Dragon>,
        private val picasso: Picasso
) : RecyclerView.Adapter<DragonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DragonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dragon_item, null, false)
        return DragonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dragons.size
    }

    override fun onBindViewHolder(holder: DragonViewHolder, position: Int) {
        val dragon = dragons[position]
        holder.bind(dragon, picasso)
    }

    fun updateDragons(dragons: List<Dragon>) {
        this.dragons as ArrayList
        this.dragons.clear()
        this.dragons.addAll(dragons)
        notifyDataSetChanged()
    }
}