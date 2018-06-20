package com.aitec.sitesport.reserve

interface ReserveRepository {

    fun getItemsReserved(fecha: String, pk: String, pkCancha: String)

    fun createReserve()
}
