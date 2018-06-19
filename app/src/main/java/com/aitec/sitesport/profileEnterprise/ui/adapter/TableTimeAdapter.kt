package com.aitec.sitesport.profileEnterprise.ui.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.enterprise.Dia
import kotlinx.android.synthetic.main.item_rv_table_time.view.*

class TableTimeAdapter(var tableTimeList: List<Dia>) : RecyclerView.Adapter<TableTimeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_table_time, parent, false);
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tableTimeList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tableTime = tableTimeList.get(position)
        holder.view.tvDia.text = tableTime.nombre
        holder.view.tvHourStart.text = "Desde ${tableTime.hora_inicio}"
        holder.view.tvHourEnd.text = "Hasta ${tableTime.hora_fin}"
    }


    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {}


}