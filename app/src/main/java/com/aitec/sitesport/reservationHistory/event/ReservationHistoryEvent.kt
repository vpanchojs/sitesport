package com.aitec.sitesport.reservationHistory.event

class ReservationHistoryEvent (var eventType: Int, var eventMsg: String?, var eventObject: Any?) {

    companion object {
        const val SUCCESS = 1
        const val ERROR = 0
    }

}