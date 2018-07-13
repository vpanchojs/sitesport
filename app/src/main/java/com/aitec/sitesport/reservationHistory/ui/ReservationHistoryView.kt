package com.aitec.sitesport.reservationHistory.ui

import com.aitec.sitesport.entities.Reservation

interface ReservationHistoryView {
    fun showLoading()
    fun hideLoading(msg: Any?)
    fun setReservations(reservations: List<Reservation>)
}