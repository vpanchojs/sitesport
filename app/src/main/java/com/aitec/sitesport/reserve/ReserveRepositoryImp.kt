package com.aitec.sitesport.reserve

import android.util.Log
import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.Reservation
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.reserve.events.ReserveEvents

class ReserveRepositoryImp(var eventBus: EventBusInterface, var firebaseApi: FirebaseApi) : ReserveRepository {

    override fun getItemsReserved(fecha: String, pk: String, pkCancha: String) {
        Log.e("repository", "fecha $fecha idcancha $pkCancha y idCentro $pk")
        firebaseApi.getItemReserved(fecha, pk, pkCancha, object : onApiActionListener<List<Reservation>> {
            override fun onSucces(response: List<Reservation>) {
                postEvent(ReserveEvents.ON_GET_ITEMS_RESERVED_SUCCESS, response)
            }

            override fun onError(error: Any?) {
                postEvent(ReserveEvents.ON_GET_ITEMS_RESERVED_ERROR, error!!)
            }
        })

    }


    override fun createReserve(reservation: Reservation) {
        firebaseApi.createReserved(reservation, object : onApiActionListener<Unit> {
            override fun onSucces(response: Unit) {
                postEvent(ReserveEvents.ON_RESERVED_SUCCESS, response)

            }

            override fun onError(error: Any?) {
                postEvent(ReserveEvents.ON_RESERVED_ERROR, error!!)
            }
        })
    }


    private fun postEvent(type: Int, any: Any) {
        var event = ReserveEvents(type, any)
        eventBus.post(event)
    }

}

