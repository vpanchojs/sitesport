package com.aitec.sitesport.reserve

import com.aitec.sitesport.entities.Reservation

interface ReserveRepository {

    fun getItemsReserved(fecha: String, pk: String, pkCancha: String)

    fun createReserve(reservation: Reservation)
}
