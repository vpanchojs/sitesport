package com.aitec.sitesport.team

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.R
import kotlinx.android.synthetic.main.item_player.view.*

class PlayerAdapter(var playersList: List<String>) : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false);
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return playersList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = playersList.get(position)
        holder.view.name.text = name
    }


    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {}


}