package com.aitec.sitesport.reserve

import com.aitec.sitesport.entities.ItemReservation

interface ReservePresenter {

    fun onSubscribe()

    fun onUnSubscribe()

    fun getItemsReserved()

    fun addItemReserve(item: ItemReservation)

    fun removeItem(item: ItemReservation)

    fun createReserve()

}
