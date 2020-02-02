package com.github.butul0ve.spacexchecker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.butul0ve.spacexchecker.R
import com.github.butul0ve.spacexchecker.adapter.viewholder.RocketViewHolder
import com.github.butul0ve.spacexchecker.db.model.Rocket

class RocketAdapter(private val rockets: List<Rocket>) : RecyclerView.Adapter<RocketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rocket_item, null, false)
        return RocketViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rockets.size
    }

    override fun onBindViewHolder(holder: RocketViewHolder, position: Int) {
        val rocket = rockets[position]
        holder.bind(rocket)
    }

    fun updateRockets(rockets: List<Rocket>) {
        this.rockets as ArrayList
        this.rockets.clear()
        this.rockets.addAll(rockets)
        notifyDataSetChanged()
    }
}