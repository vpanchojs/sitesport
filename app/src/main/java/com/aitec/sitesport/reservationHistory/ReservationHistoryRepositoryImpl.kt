package com.aitec.sitesport.reservationHistory

import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.Reservation
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.reservationHistory.event.ReservationHistoryEvent

class ReservationHistoryRepositoryImpl(val firebaseApi: FirebaseApi,
                                       val eventBusInterface: EventBusInterface,
                                       val sharePreferencesApi: SharePreferencesApi) : ReservationHistoryRepository {
    override fun getReservations() {
        firebaseApi.getReservations(object : onApiActionListener<ArrayList<Reservation>>{
            override fun onSucces(response: ArrayList<Reservation>) {
                if(response.isEmpty())
                    postEvent(ReservationHistoryEvent.SUCCESS, "No posees reservaciones", null)
                else {
                    val list: ArrayList<Reservation> = arrayListOf()
                    val head = Reservation()
                    head.type = Reservation.HEAD
                    list.add(head)
                    list.addAll(response)
                    postEvent(ReservationHistoryEvent.SUCCESS, null, list)
                }
            }

            override fun onError(error: Any?) {
                postEvent(ReservationHistoryEvent.SUCCESS, error.toString(), null)
            }

        })
    }

    private fun postEvent(type: Int, any: Any?, eventObject: Any?) {
        val event = ReservationHistoryEvent(type, any, eventObject)
        eventBusInterface.post(event)
    }

}