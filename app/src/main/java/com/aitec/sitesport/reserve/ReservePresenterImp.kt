package com.aitec.sitesport.reserve

import android.view.View
import com.aitec.sitesport.entities.ItemReservation
import com.aitec.sitesport.entities.Reservation
import com.aitec.sitesport.entities.enterprise.Cancha
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.reserve.events.ReserveEvents
import com.aitec.sitesport.reserve.ui.ReserveView
import org.greenrobot.eventbus.Subscribe
import java.util.*

class ReservePresenterImp(var eventBus: EventBusInterface, var view: ReserveView, var interactor: ReserveInteractor) : ReservePresenter {

    override fun onSubscribe() {
        eventBus.register(this)
    }

    override fun onUnSubscribe() {
        eventBus.unregister(this)
    }

    override fun getItemsReserved(fecha: Long, pkEntrepise: String, pkCancha: String) {
        view.showProgresItemsReserve(View.VISIBLE)
        view.showContainerItemsReserve(View.INVISIBLE)
        interactor.getItemsReserved(fecha, pkEntrepise, pkCancha)
    }

    override fun addItemReserve(item: ItemReservation) {
        interactor.addItemsReserve(item)
    }

    override fun removeItem(item: ItemReservation) {
        interactor.removeItem(item)
    }

    override fun createReserve(date: Date, items: List<ItemReservation>, pk_court: Cancha, price: Double, observations: String, enterprise: Enterprise) {
        view.showButtonReserve(View.INVISIBLE)
        view.showProgresBar(View.VISIBLE)
        interactor.createReserve(date, items, pk_court, price, observations, enterprise)
    }

    @Subscribe
    fun onRecibeEvents(events: ReserveEvents) {
        when (events.type) {
            ReserveEvents.ON_GET_ITEMS_RESERVED_SUCCESS -> {
                view.showContainerItemsReserve(View.VISIBLE)
                view.showProgresItemsReserve(View.INVISIBLE)
                view.setItemsReserved(events.any as List<Reservation>)
            }
            ReserveEvents.ON_GET_ITEMS_RESERVED_ERROR -> {
                view.showContainerItemsReserve(View.VISIBLE)
                view.showProgresItemsReserve(View.INVISIBLE)
                view.showMessagge(events.any)
            }

            ReserveEvents.ON_RESERVED_SUCCESS -> {
                view.showButtonReserve(View.VISIBLE)
                view.showProgresBar(View.INVISIBLE)
                view.showMessagge("Reserva realizada con exito")
            }
            ReserveEvents.ON_RESERVED_ERROR -> {
                //codificar accion
                view.showButtonReserve(View.VISIBLE)
                view.showProgresBar(View.INVISIBLE)
                view.showMessagge(events.any)
            }

        }

    }


}

