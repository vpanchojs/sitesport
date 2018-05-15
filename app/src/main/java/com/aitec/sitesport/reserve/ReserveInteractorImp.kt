package com.aitec.sitesport.reserve

import com.aitec.sitesport.entities.ItemReservation
import com.aitec.sitesport.lib.base.EventBusInterface

class ReserveInteractorImp(var repository: ReserveRepository, var eventBus: EventBusInterface) : ReserveInteractor {

    var itemsReserve = ArrayList<ItemReservation>()

    override fun getItemsReserved() {
        repository.getItemsReserved()
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

