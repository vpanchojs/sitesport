package com.aitec.sitesport.reserve

import com.aitec.sitesport.entities.ItemReservation

interface ReserveInteractor {

    fun getItemsReserved()

    fun addItemsReserve(item: ItemReservation)

    fun removeItem(item: ItemReservation)

    fun createReserve()

}