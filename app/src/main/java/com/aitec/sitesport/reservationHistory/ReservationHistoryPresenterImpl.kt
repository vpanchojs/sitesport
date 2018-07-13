package com.aitec.sitesport.reservationHistory

import android.util.Log
import com.aitec.sitesport.entities.Reservation
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.reservationHistory.event.ReservationHistoryEvent
import com.aitec.sitesport.reservationHistory.ui.ReservationHistoryView
import org.greenrobot.eventbus.Subscribe

class ReservationHistoryPresenterImpl(val reservationHistoryView: ReservationHistoryView,
                                      val reservationHistoryInteractor: ReservationHistoryInteractor,
                                      val eventBusInterface: EventBusInterface) : ReservationHistoryPresenter {


    override fun getReservations() {
        reservationHistoryView.showLoading()
        reservationHistoryInteractor.getReservations()
    }

    override fun register() {
        eventBusInterface.register(this)

    }

    override fun unregister() {
        eventBusInterface.unregister(this)
    }

    @Subscribe
    override fun onEventThread(event: ReservationHistoryEvent) {

        reservationHistoryView.hideLoading(event.eventMsg)

        when (event.eventType) {

            ReservationHistoryEvent.SUCCESS -> {
                Log.e("PRESENTER", "SUCCESS")
                reservationHistoryView.setReservations(event.eventObject as List<Reservation>)
            }

            ReservationHistoryEvent.ERROR -> {

            }

        }

    }

}