package com.aitec.sitesport.reservationHistory.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.Reservation
import com.aitec.sitesport.main.ui.MainActivity
import com.aitec.sitesport.reservationHistory.AdapterReservationHistory
import com.aitec.sitesport.reservationHistory.ReservationHistoryPresenter
import kotlinx.android.synthetic.main.fragment_record.*
import kotlinx.android.synthetic.main.fragment_record.view.*
import kotlinx.android.synthetic.main.layout_loading.view.*
import javax.inject.Inject


class ReservationHistoryFragment : Fragment(), ReservationHistoryView{

    @Inject
    lateinit var presenter: ReservationHistoryPresenter
    var reservationsList: MutableList<Reservation> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
        presenter.register()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unregister()
    }

    override fun setReservations(reservations: List<Reservation>) {
        reservationsList.clear()
        reservationsList.addAll(reservations)
        rvRecordReservation.adapter.notifyDataSetChanged()
    }

    override fun hideLoading(msg: Any?) {
        if(msg != null){
            clContentLoading.loadingReservations.pbLoading.visibility = View.GONE
            clContentLoading.loadingReservations.tvLoading.text = msg.toString()
            clContentLoading.loadingReservations.tvLoading.visibility = View.VISIBLE
            //btnReload.visibility = View.VISIBLE
        }else{
            clContentLoading.visibility = View.GONE
            rvRecordReservation.visibility = View.VISIBLE
        }
    }

    override fun showLoading() {
        rvRecordReservation.visibility = View.GONE
        //btnReload.visibility = View.GONE
        clContentLoading.loadingReservations.tvLoading.visibility = View.GONE
        clContentLoading.loadingReservations.pbLoading.visibility = View.VISIBLE
        clContentLoading.loadingReservations.visibility = View.VISIBLE
        clContentLoading.visibility = View.VISIBLE
    }

    private fun setupInjection() {
        val app: MyApplication = activity!!.application as MyApplication
        val reservationHistoryComponent = app.getReservationHistoryComponent(this)
        reservationHistoryComponent.inject(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        /*for (i in 0..3) {
            val r = Reservation()
            r.isConsumed = false
            r.court = "Cancha " + i
            r.site = "Sitio Deportivo " + i
            r.reservationDate = "Fecha de reserva " + i
            r.gameDate = "Fecha de juego " + i
            reservationsList.add(r)
        }

        for (i in 4..8) {
            val r = Reservation()
            r.isConsumed = true
            r.court = "Cancha " + i
            r.site = "Sitio Deportivo " + i
            r.reservationDate = "Fecha de reserva " + i
            r.gameDate = "Fecha de juego " + i
            reservationsList.add(r)
        }*/

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    @SuppressLint("Recycle")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AdapterReservationHistory(reservationsList)
        view.rvRecordReservation.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        view.rvRecordReservation.adapter = adapter

        btnReload.setOnClickListener {
            presenter.getReservations()
            Log.e("Reservations History", "Reintentar")
        }

        btnReload.visibility = View.GONE
        //loadingReservations.visibility = View.GONE
        // SE VA A CAER =S
        presenter.getReservations()
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
        }*/


        /*val ATTRS = intArrayOf(android.R.attr.listDivider)

        val styledAttributes: TypedArray = activity!!.obtainStyledAttributes(ATTRS)

        val dividerItemDecoration = ItemDecorator(styledAttributes.getDrawable(0))
        //view.rvRecordReservation.addItemDecoration(dividerItemDecoration)
        view.rvRecordReservation.addItemDecoration(dividerItemDecoration, 3)
        //clLayoutLoading.loadingReservations.tvLoading.text = "Próximamente... " + String(Character.toChars(ProfileActivity.EMOTICON_EYE))*/

    }

    companion object {
        fun newInstance(): ReservationHistoryFragment {
            val fragment = ReservationHistoryFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


}
