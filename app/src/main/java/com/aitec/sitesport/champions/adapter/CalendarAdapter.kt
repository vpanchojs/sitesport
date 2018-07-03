package com.aitec.sitesport.champions.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.ItemCalendar
import com.aitec.sitesport.entities.Team
import kotlinx.android.synthetic.main.item_calendar.view.*
import kotlinx.android.synthetic.main.item_calendar_date.view.*

class CalendarAdapter(var itemCalendar: List<Any>, var callback: onCalendarAdapterListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = itemCalendar[position]
        if(item is ItemCalendar) {
            item = item as ItemCalendar
            val h = holder as ViewHolder

            h.view.tv_date.text = item.fecha
            h.view.tv_time.text = "${item.hora}:00"
            h.view.tv_genero.text = item.genero

            when (item.estado) {
                ItemCalendar.JUGANDO -> {
                    h.view.tv_state.text = "JUGANDO"
                }
                ItemCalendar.FINALIZO -> {
                    h.view.tv_state.text = "FINALIZO"
                }
                ItemCalendar.SIN_JUGAR -> {
                    h.view.tv_state.text = "PENDIENTE"
                }
            }


            h.view.btn_team1.text = item.equipo1.nombre
            h.view.btn_team2.text = item.equipo2.nombre

            if (item.estado == ItemCalendar.FINALIZO) {
                h.view.tv_result.text = "${item.equipo1.marcador} - ${item.equipo2.marcador}"
            } else {
                h.view.tv_result.text = "VS"
            }

            h.view.btn_team1.setOnClickListener {
                callback.navigatioTeam(item.equipo1)
            }

            h.view.btn_team2.setOnClickListener {
                callback.navigatioTeam(item.equipo2)
            }
        }else if(item is String){
            val h = holder as HeadHolder
            h.view.tvDate.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)


        when (viewType) {
            0 -> {
                val view = inflater.inflate(R.layout.item_calendar, parent, false) as ViewGroup
                return CalendarAdapter.ViewHolder(view)
            }

            1 -> {
                val view = inflater.inflate(R.layout.item_calendar_date, parent, false) as ViewGroup
                return CalendarAdapter.HeadHolder(view)
            }

            else -> {
                val view = inflater.inflate(R.layout.item_calendar, parent, false) as ViewGroup
                return CalendarAdapter.ViewHolder(view)
            }
        }
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar, parent, false)
        //return CalendarAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemCalendar.size
    }


    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {}
    class HeadHolder(var view: View) : RecyclerView.ViewHolder(view) {}
}

interface onCalendarAdapterListener {
    fun navigatioTeam(team: Team)
}
