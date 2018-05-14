package com.aitec.sitesport.profile.ui.dialog

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.Rate
import kotlinx.android.synthetic.main.item_rv_rate_court.view.*

class RateCourtAdapter (val context: Context, var listRatesCourt: List<Rate>) : RecyclerView.Adapter<RateCourtAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_rate_court, parent, false);
        return RateCourtAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listRatesCourt.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rateCourt = listRatesCourt[position]

        var day = ""

        when(rateCourt.nameDay){
            0 -> day = "Lunes"
            1 -> day = "Martes"
            2 -> day = "Miércoles"
            3 -> day = "Jueves"
            4 -> day = "Viernes"
            5 -> day = "Sábado"
            6 -> day = "Domingo"
        }

        holder.view.tvNameDay.text = day
        holder.view.tvPriceDay.text = rateCourt.priceDay
        holder.view.tvRankDay.text = rateCourt.rankDay

        holder.view.tvPriceNight.text = rateCourt.priceNight
        holder.view.tvRankNight.text = rateCourt.rankNight

    }


    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {}

}