package com.aitec.sitesport.champions.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.ItemCalendar
import kotlinx.android.synthetic.main.item_calendar.view.*

class CalendarAdapter(var itemCalendar: List<ItemCalendar>) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemCalendar.get(position)
        holder.view.tv_date.text = item.fecha
        holder.view.tv_time.text = "${item.hora}:00"
        holder.view.tv_genero.text = item.genero

        when (item.estado) {
            ItemCalendar.JUGANDO -> {
                holder.view.tv_state.text = "JUGANDO"
            }
            ItemCalendar.FINALIZO -> {
                holder.view.tv_state.text = "FINALIZO"
            }
            ItemCalendar.SIN_JUGAR -> {
                holder.view.tv_state.text = "PENDIENTE"
            }
        }


        holder.view.btn_team1.text = item.equipo1.nombre
        holder.view.btn_team2.text = item.equipo2.nombre

        if (item.estado == ItemCalendar.FINALIZO) {
            holder.view.tv_result.text = "${item.equipo1.marcador} - ${item.equipo2.marcador}"
        } else {
            holder.view.tv_result.text = "VS"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar, parent, false)
        return CalendarAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemCalendar.size
    }


    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    }
}
