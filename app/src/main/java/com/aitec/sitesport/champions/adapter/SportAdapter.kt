package com.aitec.sitesport.champions.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.Sport
import kotlinx.android.synthetic.main.item_court.view.*


class SportAdapter(var sportList: List<Sport>, var callback: onSelectItemSport) : RecyclerView.Adapter<SportAdapter.ViewHolder>() {
    lateinit var rbSelect: RadioButton

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sport, parent, false)
        return SportAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sportList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sport = sportList.get(position)
        holder.view.rb_court.text = sport.nombre

        holder.view.rb_court.setOnCheckedChangeListener { compoundButton, b ->

            if (::rbSelect.isInitialized) {
                rbSelect.isChecked = false
            }

            rbSelect = compoundButton as RadioButton

            Log.e("selec", "se seleciono")


            if (b) {

                callback.onSelectSport(sport)
            }

        }

        if (position == 0) {

            holder.view.rb_court.isChecked = true
        }

    }


    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    }


    interface onSelectItemSport {
        fun onSelectSport(sport: Sport)
    }

}
