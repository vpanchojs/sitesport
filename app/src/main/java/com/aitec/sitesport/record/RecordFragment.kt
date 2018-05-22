package com.aitec.sitesport.record

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.aitec.sitesport.R
import com.aitec.sitesport.entities.Reservation
import com.aitec.sitesport.profile.ui.ProfileActivity
import kotlinx.android.synthetic.main.fragment_record.*
import kotlinx.android.synthetic.main.fragment_record.view.*


class RecordFragment : Fragment() {

    var reservationsList: MutableList<Reservation> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        for(i in 0..3){
            val r = Reservation()
            r.isConsumed = false
            r.court = "Cancha " + i
            r.site = "Sitio Deportivo " + i
            r.reservationDate = "Fecha de reserva " + i
            r.gameDate = "Fecha de juego " + i
            reservationsList.add(r)
        }

        for(i in 4..8){
            val r = Reservation()
            r.isConsumed = true
            r.court = "Cancha " + i
            r.site = "Sitio Deportivo " + i
            r.reservationDate = "Fecha de reserva " + i
            r.gameDate = "Fecha de juego " + i
            reservationsList.add(r)
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    @SuppressLint("Recycle")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*if(reservationsList.size == 0) return

        val orderReservationsToView: MutableList<Reservation> = mutableListOf()

        val h = Reservation()
        h.type = Reservation.HEAD
        h.head = "Nuevas reservas"
        orderReservationsToView.add(h)
        for(r in reservationsList){
            if(!r.isConsumed){
                orderReservationsToView.add(r)
            }
        }

        val r = Reservation()
        r.type = Reservation.HEAD
        r.head = "Reservas antiguas"
        orderReservationsToView.add(r)
        for(r in reservationsList){
            if(r.isConsumed){
                orderReservationsToView.add(r)
            }
        }

        val adapter = AdapterRecord(orderReservationsToView)
        view.rvRecordReservation.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        view.rvRecordReservation.adapter = adapter

        /*val ATTRS = intArrayOf(android.R.attr.listDivider)

        val styledAttributes: TypedArray = activity!!.obtainStyledAttributes(ATTRS)

        val dividerItemDecoration = ItemDecorator(styledAttributes.getDrawable(0))
        //view.rvRecordReservation.addItemDecoration(dividerItemDecoration)
        view.rvRecordReservation.addItemDecoration(dividerItemDecoration, 3)*/*/
        tvInfo.text = "Próximamente... " + String(Character.toChars(ProfileActivity.EMOTICON_EYE))

    }

    companion object {
        fun newInstance(): RecordFragment {
            val fragment = RecordFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


}
