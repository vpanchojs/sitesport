package com.aitec.sitesport.reserve

import com.aitec.sitesport.entities.ItemReservation
import com.aitec.sitesport.entities.enterprise.Cancha
import java.util.*

interface ReserveInteractor {

    fun getItemsReserved(fecha: Long, pk: String, pkCancha: String)

    fun addItemsReserve(item: ItemReservation)

    fun removeItem(item: ItemReservation)

    fun createReserve(date: Date, items: List<ItemReservation>, pk_court: Cancha, price: Double, observations: String)

}
