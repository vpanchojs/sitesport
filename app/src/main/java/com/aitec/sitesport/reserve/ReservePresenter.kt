package com.aitec.sitesport.reserve

import com.aitec.sitesport.entities.ItemReservation
import com.aitec.sitesport.entities.enterprise.Cancha
import java.util.*

interface ReservePresenter {

    fun onSubscribe()

    fun onUnSubscribe()

    fun getItemsReserved(fecha: Long, pkEntrepise: String, pkCancha: String)

    fun addItemReserve(item: ItemReservation)

    fun removeItem(item: ItemReservation)

    fun createReserve(date: Date, items: List<ItemReservation>, court: Cancha, price: Double, observations: String)

}
