package com.aitec.sitesport.reservationHistory

class ReservationHistoryInteractorImpl(val reservationHistoryRepository: ReservationHistoryRepository) :  ReservationHistoryInteractor{
    override fun getReservations() {
        reservationHistoryRepository.getReservations()
    }
}