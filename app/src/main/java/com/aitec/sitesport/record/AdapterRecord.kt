package com.aitec.sitesport.record

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.R

class AdapterRecord(var ReservationsList: List<String>) : RecyclerView.Adapter<AdapterRecord.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /*override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_rv_table_time, parent, false);
        return AdapterRecord.ViewHolder(view)
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

    }*/


    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {}
}