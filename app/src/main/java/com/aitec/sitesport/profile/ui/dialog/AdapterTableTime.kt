package com.aitec.sitesport.profile.ui.dialog

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.enterprise.Dia
import com.aitec.sitesport.entities.enterprise.Horario
import com.aitec.sitesport.reserve.adapter.CourtAdapter
import kotlinx.android.synthetic.main.fragment_table_time.view.*
import kotlinx.android.synthetic.main.item_rv_table_time.view.*

class AdapterTableTime(var tableTimeList: List<Dia>) : RecyclerView.Adapter<AdapterTableTime.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_rv_table_time, parent, false);
        return AdapterTableTime.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tableTimeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tableTime = tableTimeList.get(position)

        var dia = ""
        when(tableTime.nombre){
            0 -> dia = "Lunes"
            1 -> dia = "Martes"
            2 -> dia = "Miércoles"
            3 -> dia = "Jueves"
            4 -> dia = "Viernes"
            5 -> dia = "Sábado"
            6 -> dia = "Domingo"
        }

        holder.view.tvDia.text = dia
        holder.view.tvHourStart.text = "Desde " + tableTime.horas[0].inicio
        holder.view.tvHourEnd.text = "Hasta " + tableTime.horas[0].fin

        Log.e("onBindViewHolder", position.toString() + "  -  " + tableTime.horas[0].inicio.toString())

    }


    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {}


}