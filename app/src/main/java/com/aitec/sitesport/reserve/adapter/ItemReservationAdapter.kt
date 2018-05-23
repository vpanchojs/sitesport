package com.aitec.sitesport.reserve.adapter

import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.ItemReservation
import kotlinx.android.synthetic.main.item_time_table.view.*


class ItemReservationAdapter(var hoursList: List<ItemReservation>) : RecyclerView.Adapter<ItemReservationAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_time_table, parent, false);
        return ItemReservationAdapter.ViewHolder(view, hoursList)
    }

    override fun getItemCount(): Int {
        return hoursList.size
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        val hours = hoursList.get(position)
        holder.view.tv_time_table.setText("${hours.start} a ${hours.end} ")

        if (hours.state) {
            holder.view.tv_state.setText("Reservado")
            holder.view.cb_item.isEnabled = false
        } else {
            holder!!.view.tv_state.setText("Disponible")
            holder.view.cb_item.isEnabled = true
        }
    }

    class ViewHolder(var view: View, var hoursList: List<ItemReservation>) : RecyclerView.ViewHolder(view) {

    }

}
