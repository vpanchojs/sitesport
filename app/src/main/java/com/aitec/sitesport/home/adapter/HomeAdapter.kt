package com.aitec.sitesport.home.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.Publications
import com.aitec.sitesport.util.GlideApp
import kotlinx.android.synthetic.main.item_home.view.*

/**
 * Created by Jhony on 21 may 2018.
 */




class HomeAdapter (var data: ArrayList<Publications>?, var callback: onHomeAdapterListener) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

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
                .load(data!!.get(position).photo)
                .placeholder(R.drawable.ic_sites)
                .centerCrop()
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(holder!!.view.img_imagen)

        holder!!.view.txttitle.text = data!!.get(position).title
        holder!!.view.txtfecha.text = data!!.get(position).fecha

        GlideApp.with(context)
                .load(data!!.get(position).imageIcon)
                .placeholder(R.drawable.ic_sites)
                .centerCrop()
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(holder!!.view.civ_center)

        holder!!.view.txtdescription.text = data!!.get(position).description
        holder!!.onNavigationProfile(position, callback)

        holder.view.btn_home_share.setOnClickListener {

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
}

