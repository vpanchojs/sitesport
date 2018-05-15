package com.aitec.sitesport.reserve

import android.view.View
import com.aitec.sitesport.entities.ItemReservation
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.reserve.events.ReserveEvents
import com.aitec.sitesport.reserve.ui.ReserveView
import org.greenrobot.eventbus.Subscribe

class ReservePresenterImp(var eventBus: EventBusInterface, var view: ReserveView, var interactor: ReserveInteractor) : ReservePresenter {

    override fun onSubscribe() {
        eventBus.register(this)
    }

    override fun onUnSubscribe() {
        eventBus.unregister(this)
    }

    override fun getItemsReserved() {
        view.showProgresItemsReserve(View.VISIBLE)
        view.showContainerItemsReserve(View.INVISIBLE)
        interactor.getItemsReserved()
    }

    override fun addItemReserve(item: ItemReservation) {
        interactor.addItemsReserve(item)
    }

    override fun removeItem(item: ItemReservation) {
        interactor.removeItem(item)
    }

    override fun createReserve() {
        view.showButtonReserve(View.INVISIBLE)
        view.showProgresBar(View.VISIBLE)
    }

    @Subscribe
    fun onRecibeEvents(events: ReserveEvents) {
        when (events.type) {
            ReserveEvents.ON_GET_ITEMS_RESERVED_SUCCESS -> {
                view.showContainerItemsReserve(View.VISIBLE)
                view.showProgresItemsReserve(View.INVISIBLE)
            }
        }

    }

}

