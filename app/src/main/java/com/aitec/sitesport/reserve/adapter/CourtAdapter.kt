package com.aitec.sitesport.reserve.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.Courts
import kotlinx.android.synthetic.main.item_court.view.*


class CourtAdapter(var courtsList: List<Courts>, var callback: onCourtAdapterListener) : RecyclerView.Adapter<CourtAdapter.ViewHolder>() {
    lateinit var rb_select: RadioButton


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_court, parent, false);
        return CourtAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courtsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val courts = courtsList.get(position)
        holder.view!!.rb_court.text = courts.name

        holder.view.rb_court.setOnCheckedChangeListener { compoundButton, b ->

            if (::rb_select.isInitialized) {
                rb_select.isChecked = false
            }

            rb_select = compoundButton as RadioButton
        }

        //holder.onListener(courts, callback)
    }


    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun onListener(courts: Courts, callback: onCourtAdapterListener) {


        }
    }

    interface onCourtAdapterListener {
        fun setData(courts: Courts)
    }


}
