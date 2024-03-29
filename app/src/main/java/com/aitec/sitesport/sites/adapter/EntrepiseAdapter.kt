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

    val TAG = javaClass.simpleName

    val df = DecimalFormat("0.00")
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        when (viewType) {
        //Enterprise
            Enterprise.TYPE_ENTREPISE -> {
                return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_entrepise, parent, false))
            }
        //Space
            Enterprise.TYPE_SPACE -> {
                return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_space, parent, false))
            }
            else -> return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_entrepise, parent, false))
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return data.get(position).viewType
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entrepise = data.get(position)

        when (entrepise.viewType) {
            Enterprise.TYPE_ENTREPISE -> {
                holder.view.tv_name_entrepise.text = entrepise.nombre

                GlideApp.with(context)
                        .load(entrepise.foto_perfil.trim())
                        .placeholder(R.drawable.ic_sites)
                        .centerCrop()
                        .error(R.drawable.ic_error_outline_black_24dp)
                        .into(holder.view.iv_entrepise)

                holder.view.tv_address.text = entrepise.direccion?.calles

                holder.view.tv_raiting.text = entrepise.me_gustas.toString()


                if (entrepise.distancia > 0) {
                    holder.view.tv_distance.text = "${df.format(entrepise.distancia)} km"
                    holder.view.tv_distance.visibility = View.VISIBLE
                } else {
                    holder.view.tv_distance.visibility = View.GONE
                }


                if (entrepise.abierto) {
                    holder.view.iv_open.visibility = View.VISIBLE
                } else {
                    holder.view.iv_open.visibility = View.GONE
                }

                holder.onNavigationProfile(entrepise, callback)
            }
            Enterprise.TYPE_SPACE -> {

            }
        }


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