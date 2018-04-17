package com.aitec.sitesport.reserve.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.R
import com.aitec.sitesport.reserve.ReserveActivity
import kotlinx.android.synthetic.main.item_time_table.view.*


class TableTimeAdapter(var hoursList: List<ReserveActivity.Hours>, var callback: onTableTimeAdapterListener) : RecyclerView.Adapter<TableTimeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_time_table, parent, false);
        return TableTimeAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hoursList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hours = hoursList.get(position)
        holder!!.view.tv_time_table.setText("${hours.start} a ${hours.end} ")

        if (hours.state)
            holder!!.view.tv_state.setText("Reservado")
        else
            holder!!.view.tv_state.setText("Disponible")
    }


    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {


    }

    interface onTableTimeAdapterListener {

    }


}
