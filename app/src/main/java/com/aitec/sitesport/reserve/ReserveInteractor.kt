package com.aitec.sitesport.reserve

import com.aitec.sitesport.entities.ItemReservation

interface ReserveInteractor {

    fun getItemsReserved(fecha: Long, pk: String, pkCancha: String)

    fun addItemsReserve(item: ItemReservation)

    fun removeItem(item: ItemReservation)

    fun createReserve()

}
