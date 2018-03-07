package com.aitec.sitesport.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.Entrepise
import kotlinx.android.synthetic.main.item_entrepise.view.*

/**
 * Created by victor on 6/3/18.
 */
class EntrepiseAdapter(var data: ArrayList<Entrepise>, var callback: onEntrepiseAdapterListener) : RecyclerView.Adapter<EntrepiseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_entrepise, parent, false);
        return ViewHolder(view);
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var entrepise = data.get(position)
        holder!!.view.tv_name_entrepise.text = entrepise.name
        holder!!.view.tv_distance.text = "5 km"

    }


    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    }

    interface onEntrepiseAdapterListener {
        fun navigatioProfile(idEntrepise: Any)
    }
}