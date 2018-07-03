package com.aitec.sitesport.home.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.Publication
import com.aitec.sitesport.util.GlideApp
import kotlinx.android.synthetic.main.item_home.view.*

/**
 * Created by Jhony on 21 may 2018.
 */


class HomeAdapter(var data: ArrayList<Publication>?, var callback: onHomeAdapterListener) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent!!.context
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_home, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        GlideApp.with(context)
                .load(data!!.get(position).foto)
                .placeholder(R.drawable.ic_bg_balon)
                .centerCrop()
                .error(R.drawable.ic_bg_balon)
                .into(holder.view.img_imagen)

        holder.view.txttitle.text = data!!.get(position).nombre_centro_deportivo
        holder.view.txtfecha.text = data!!.get(position).fecha

        GlideApp.with(context)
                .load(data!!.get(position).icon)
                .placeholder(R.mipmap.ic_launcher_round)
                .centerCrop()
                .error(R.mipmap.ic_launcher_round)
                .into(holder.view.civ_center)

        holder.view.txtdescription.text = data!!.get(position).titulo
        holder.onNavigationProfile(position, callback)

        holder.view.btn_home_share.setOnClickListener {
            callback.sharePublication(data!!.get(position))
        }
    }


    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        fun onNavigationProfile(position: Int, callback: onHomeAdapterListener) {
            view.setOnClickListener({
                callback.navigatioProfile(position)
            })
        }
    }
}

interface onHomeAdapterListener {
    fun navigatioProfile(position: Int)
    fun sharePublication(publication: Publication)
}

