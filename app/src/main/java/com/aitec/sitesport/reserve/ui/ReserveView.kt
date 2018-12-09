package com.aitec.sitesport.reserve.ui

import com.aitec.sitesport.entities.ItemReservation
import com.aitec.sitesport.entities.Reservation

interface ReserveView {
    fun showMessagge(message: Any)
    fun showProgresBar(show: Int)
    fun showButtonReserve(show: Int)
    fun setItemsReserved(itemsReserved: List<Reservation>)
    fun showProgresItemsReserve(visible: Int)
    fun showContainerItemsReserve(visible: Int)
}