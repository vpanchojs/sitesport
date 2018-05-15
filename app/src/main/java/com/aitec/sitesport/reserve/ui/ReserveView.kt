package com.aitec.sitesport.reserve.ui

import com.aitec.sitesport.entities.ItemReservation

interface ReserveView {
    fun showMessagge(message: Any)
    fun showProgresBar(show: Int)
    fun showButtonReserve(show: Int)
    fun setItemsReserved(itemsReserved: List<ItemReservation>)
    fun showProgresItemsReserve(visible: Int)
    fun showContainerItemsReserve(visible: Int)
}