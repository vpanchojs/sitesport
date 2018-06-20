package com.aitec.sitesport.reserve

import android.util.Log
import com.aitec.sitesport.entities.ItemReservation
import com.aitec.sitesport.lib.base.EventBusInterface
import java.text.SimpleDateFormat
import java.util.*

class ReserveInteractorImp(var repository: ReserveRepository, var eventBus: EventBusInterface) : ReserveInteractor {

    var itemsReserve = ArrayList<ItemReservation>()

    override fun getItemsReserved(fecha: Long, pk: String, pkCancha: String) {
        val formateador = SimpleDateFormat("dd/MM/yy", Locale("ES"))
        repository.getItemsReserved(formateador.format(fecha), pk, pkCancha)
    }

    override fun addItemsReserve(item: ItemReservation) {
        itemsReserve.add(item)
    }

    override fun removeItem(item: ItemReservation) {
        itemsReserve.remove(item)
    }

    override fun createReserve() {

    }

}

