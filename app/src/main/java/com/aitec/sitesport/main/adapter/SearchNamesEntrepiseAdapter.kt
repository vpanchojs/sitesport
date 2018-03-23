package com.aitec.sitesport.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.enterprise.Enterprise
import kotlinx.android.synthetic.main.item_entrepise_search_name.view.*

/**
 * Created by victor on 21/3/18.
 */
class SearchNamesEntrepiseAdapter(var data: ArrayList<Enterprise>, var callback: onEntrepiseSearchNameListener) : RecyclerView.Adapter<SearchNamesEntrepiseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SearchNamesEntrepiseAdapter.ViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_entrepise_search_name, parent, false);
        return SearchNamesEntrepiseAdapter.ViewHolder(view);
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SearchNamesEntrepiseAdapter.ViewHolder?, position: Int) {
        var entrepise = data.get(position)
        holder!!.view.tv_name_entrepise.text = entrepise.nombres
        holder!!.onNavigationProfile(entrepise, callback!!)
    }


    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun onNavigationProfile(enterprise: Enterprise, callback: onEntrepiseSearchNameListener) {
            view.setOnClickListener {
                callback.navigatioProfile(enterprise)
            }
        }
    }

    interface onEntrepiseSearchNameListener {
        fun navigatioProfile(entrepise: Enterprise)
    }

}