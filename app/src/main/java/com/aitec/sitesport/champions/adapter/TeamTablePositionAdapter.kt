package com.aitec.sitesport.champions.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.Team
import kotlinx.android.synthetic.main.item_position_team.view.*

class TeamTablePositionAdapter(var teams: List<Team>) : RecyclerView.Adapter<TeamTablePositionAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_position_team, parent, false);
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(h: ViewHolder, position: Int) {
        val team = teams.get(position)
        h.view.tvPosition.text = "5" // CALCULAR POSICION
        h.view.tvName.text = team.nombre
        h.view.tvGroup.text = team.grupo
        h.view.tvPj.text = team.pj.toString()
        h.view.tvPoints.text = team.pu.toString()
        h.view.tvDif.text = team.dif.toString()
    }


    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {}


}