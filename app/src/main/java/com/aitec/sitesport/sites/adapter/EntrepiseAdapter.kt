package com.aitec.sitesport.sites.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.util.GlideApp
import kotlinx.android.synthetic.main.item_entrepise.view.*
import java.text.DecimalFormat

/**
 * Created by victor on 6/3/18.
 */
class EntrepiseAdapter(var data: ArrayList<Enterprise>, var callback: onEntrepiseAdapterListener) : RecyclerView.Adapter<EntrepiseAdapter.ViewHolder>() {

    val df = DecimalFormat("0.00")
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        context = parent!!.context
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_entrepise, parent, false);
        return ViewHolder(view);
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var entrepise = data.get(position)
        holder!!.view.tv_name_entrepise.text = entrepise.nombres
        //holder!!.view.tv_address.text = df.format(entrepise.address.direction) + " Km"

        GlideApp.with(context)
                .load(entrepise.foto_perfil.trim())
                .placeholder(R.drawable.ic_sites)
                .centerCrop()
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(holder!!.view.iv_entrepise)

        holder!!.view.tv_address.text = entrepise.address.calles
        holder!!.onNavigationProfile(entrepise, callback)

    }


    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun onNavigationProfile(enterprise: Enterprise, callback: onEntrepiseAdapterListener) {
            view.setOnClickListener {
                callback.navigatioProfile(enterprise)
            }
        }
    }

    interface onEntrepiseAdapterListener {
        fun navigatioProfile(entrepise: Enterprise)
    }
}