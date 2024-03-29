package com.aitec.sitesport.reservationHistory

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.Reservation
import kotlinx.android.synthetic.main.item_rv_record_reservation.view.*
import kotlinx.android.synthetic.main.item_rv_record_reservation_head.view.*

class AdapterReservationHistory(var reservationsList: List<Reservation>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            Reservation.HEAD -> {
                val view = inflater.inflate(R.layout.item_rv_record_reservation_head, parent, false) as ViewGroup
                return HeadViewHolder(view)
            }

            Reservation.RESERVATION -> {
                val view = inflater.inflate(R.layout.item_rv_record_reservation, parent, false) as ViewGroup
                return ReservationViewHolder(view)
            }

            else -> {
                val view = inflater.inflate(R.layout.item_rv_record_reservation_head, parent, false) as ViewGroup
                return HeadViewHolder(view)
            }
        }

        //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_record_reservation, parent, false);
        //return AdapterReservationHistory.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reservationsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val reservation = reservationsList[position]


        if (reservation.type == Reservation.RESERVATION) {
            val h = holder as ReservationViewHolder
            h.view.tvSite.text = reservation.centro_deportivo!!.nombre
            h.view.tvCourt.text = reservation.cancha!!.nombre
            h.view.tvReservationDate.text = "${reservation.hora_reserva}:00 - ${reservation.fecha_reserva}"
            when(reservation.estado){
                Reservation.PAGADO -> {h.view.tvState.text = "Pagado"}
                Reservation.NO_PAGADO -> {h.view.tvState.text = "Debe"}
            }

        } else if (reservation.type == Reservation.HEAD) {
            val h = holder as HeadViewHolder
            h.view.tvHead.text = "Por jugar"
        }

    }

    override fun getItemViewType(position: Int): Int {
        return reservationsList[position].type
    }

    fun getItem(index: Int): Reservation {
        return reservationsList[index]
    }

    class ReservationViewHolder(var view: View) : RecyclerView.ViewHolder(view) {}
    class HeadViewHolder(var view: View) : RecyclerView.ViewHolder(view) {}

}