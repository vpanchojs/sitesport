package com.aitec.sitesport.reservationHistory

import com.aitec.sitesport.reservationHistory.event.ReservationHistoryEvent

interface ReservationHistoryPresenter {

    fun register()
    fun unregister()
    fun onEventThread(event: ReservationHistoryEvent)
}