package com.aitec.sitesport.reserve

import com.aitec.sitesport.entities.ItemReservation
import com.aitec.sitesport.entities.Reservation
import com.aitec.sitesport.entities.enterprise.Cancha
import com.aitec.sitesport.lib.base.EventBusInterface
import java.text.SimpleDateFormat
import java.util.*

class ReserveInteractorImp(var repository: ReserveRepository, var eventBus: EventBusInterface) : ReserveInteractor {

    var itemsReserve = ArrayList<ItemReservation>()

    override fun getItemsReserved(fecha: Long, pk: String, pkCancha: String) {
        val formateador = SimpleDateFormat("dd/MM/yy", Locale("ES"))
        repository.getItemsReserved(formateador.format(fecha), pk, pkCancha)
    }

    override fun addItemsReserve(item: ItemReservation) {
        itemsReserve.add(item)
    }

    override fun removeItem(item: ItemReservation) {
        itemsReserve.remove(item)
    }

    override fun createReserve(date: Date, items: List<ItemReservation>, court: Cancha, price: Double, observations: String) {
        val reservation = Reservation()
        reservation.cancha = court
        reservation.hora_reserva = items[0].start.toInt()
        reservation.estado = "Ocupado"
        reservation.horas_juego = 1

    }
}

