package com.aitec.sitesport.mapSites.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.enterprise.Enterprise
import kotlinx.android.synthetic.main.item_entrepise_map.view.*
import java.text.DecimalFormat

/**
 * Created by victor on 6/3/18.
 */
class EntrepiseAdapter(var data: ArrayList<Enterprise>, var callback: onEntrepiseAdapterListener) : RecyclerView.Adapter<EntrepiseAdapter.ViewHolder>() {

    val df = DecimalFormat("0.00")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_entrepise_map, parent, false);
        return ViewHolder(view);
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entrepise = data.get(position)
        holder.view.tv_name_entrepise.text = entrepise.nombre


        if (entrepise.distancia > 0.0) {
            holder.view.tv_distance.text = df.format(entrepise.distancia) + " Km"
        }else{
            holder.view.tv_distance.text = ""
        }

        holder.view.tv_address.text = entrepise.direccion!!.calles
        if (entrepise.idMarker.isBlank()) {
            holder.addMarker(entrepise, callback)
        }

        holder.onNavigationProfile(entrepise, callback)
    }


    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        fun addMarker(enterprise: Enterprise, callback: onEntrepiseAdapterListener) {
            callback.addMarker(enterprise)
        }

        fun onNavigationProfile(enterprise: Enterprise, callback: onEntrepiseAdapterListener) {
            view.setOnClickListener {
                callback.navigatioProfile(enterprise)
            }
        }
    }

    interface onEntrepiseAdapterListener {
        fun navigatioProfile(entrepise: Enterprise)
        fun addMarker(enterprise: Enterprise)
    }
}