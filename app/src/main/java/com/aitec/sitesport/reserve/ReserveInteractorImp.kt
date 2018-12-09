package com.aitec.sitesport.reserve

import android.annotation.SuppressLint
import android.util.Log
import com.aitec.sitesport.entities.ItemReservation
import com.aitec.sitesport.entities.Reservation
import com.aitec.sitesport.entities.enterprise.Cancha
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.lib.base.EventBusInterface
import java.text.SimpleDateFormat
import java.util.*

class ReserveInteractorImp(var repository: ReserveRepository, var eventBus: EventBusInterface) : ReserveInteractor {

    var itemsReserve = ArrayList<ItemReservation>()

    override fun getItemsReserved(fecha: Long, pk: String, pkCancha: String) {
        val formateador = SimpleDateFormat("d/MM/yyyy", Locale("ES"))
        repository.getItemsReserved(formateador.format(fecha), pk, pkCancha)
    }

    override fun addItemsReserve(item: ItemReservation) {
        itemsReserve.add(item)
    }

    override fun removeItem(item: ItemReservation) {
        itemsReserve.remove(item)
    }

    @SuppressLint("SimpleDateFormat")
    override fun createReserve(date: Date, items: List<ItemReservation>, court: Cancha, price: Double, observations: String, enterprise: Enterprise) {

        val reservation = Reservation()
        //pk= pk_centro_deportivo + pk_cancha + fecha_reserva+ hora_reserva
        reservation.pk = "${enterprise.pk}${court.pk}${SimpleDateFormat("ddMMyyyy").format(date)}${items[0].start}"
        Log.e("pk_reserva", reservation.pk)
        reservation.fecha_reserva = SimpleDateFormat("d/MM/yyyy").format(date)
        reservation.cancha = court
        reservation.hora_reserva = items[0].start
        reservation.hora_reserva = items[0].start.toInt()
        reservation.estado = Reservation.OCUPADO
        reservation.horas_juego = 1
        reservation.centro_deportivo = enterprise
        reservation.precio = items[0].price

        repository.createReserve(reservation)
    }

    /*
    fun calculatePrice(items: List<ItemReservation>): Pair<Double, String> {
        var price = 0.0
        var horas = ""
        items.forEach {
            price += it.price
            horas += "${it.start} a ${it.end} "
        }

    }*/
}

