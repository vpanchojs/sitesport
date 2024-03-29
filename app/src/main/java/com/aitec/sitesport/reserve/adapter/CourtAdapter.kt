package com.aitec.sitesport.reserve.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.enterprise.Cancha
import kotlinx.android.synthetic.main.item_court.view.*


class CourtAdapter(var courtsList: List<Cancha>, var callback: OnClickListenerCourt) : RecyclerView.Adapter<CourtAdapter.ViewHolder>() {
    lateinit var rbSelect: RadioButton

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_court, parent, false)
        return CourtAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courtsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val courts = courtsList.get(position)
        holder.view.rb_court.text = courts.nombre

        holder.view.rb_court.setOnCheckedChangeListener { compoundButton, b ->

            if (::rbSelect.isInitialized) {
                rbSelect.isChecked = false
            }

            rbSelect = compoundButton as RadioButton

            if (b) {
                callback.onCheckedCourt(courts)
            }

        }

        if (position == 0) {
            holder.view.rb_court.isChecked = true
        }


        //holder.onListener(courts, callback)
    }


    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    }


}
