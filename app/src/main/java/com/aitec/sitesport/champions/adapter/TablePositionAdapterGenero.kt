package com.aitec.sitesport.champions.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.Team
import kotlinx.android.synthetic.main.item_table_position.view.*

class TablePositionAdapterGenero(var items: List<Team>) : RecyclerView.Adapter<TablePositionAdapterGenero.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TablePositionAdapterGenero.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_table_position, parent, false)
        return TablePositionAdapterGenero.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.get(position)
        holder.view.tv_posicion.text = (position + 1).toString()
        holder.view.tv_nombre_equipo.text = item.nombre
        holder.view.tv_pj.text = item.pj.toString()
        holder.view.tv_pu.text = item.pu.toString()
        holder.view.tv_dif.text = item.dif.toString()
    }


    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    }
}
