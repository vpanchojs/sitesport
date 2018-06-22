package com.aitec.sitesport.reservationHistory

import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.reservationHistory.event.ReservationHistoryEvent
import com.aitec.sitesport.reservationHistory.ui.ReservationHistoryView
import org.greenrobot.eventbus.Subscribe

class ReservationHistoryPresenterImpl(val reservationHistoryView: ReservationHistoryView,
                                      val reservationHistoryInteractor: ReservationHistoryInteractor,
                                      val eventBusInterface: EventBusInterface) : ReservationHistoryPresenter {
    override fun register() {
        eventBusInterface.register(this)

    }

    override fun unregister() {
        eventBusInterface.unregister(this)
    }

    @Subscribe
    override fun onEventThread(event: ReservationHistoryEvent) {

        when (event.eventType) {

            ReservationHistoryEvent.SUCCESS -> {

            }

            ReservationHistoryEvent.SUCCESS -> {

            }

        }

    }

}