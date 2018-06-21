package com.aitec.sitesport.sites

import android.view.View
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.sites.event.SitesEvents
import com.aitec.sitesport.sites.ui.SitesView
import org.greenrobot.eventbus.Subscribe

class SitesPresenterImp(var eventBus: EventBusInterface, var view: SitesView, var interactor: SitesInteractor) : SitesPresenter {

    override fun subscribe() {
        eventBus.register(this)
    }

    override fun unSubscribe() {
        eventBus.unregister(this)
    }

    override fun onGetSites() {
        view.showProgresBar(true)
        view.showButtonReload(View.GONE)
        view.showFilters(View.GONE)
        interactor.onGetSites()
    }

    override fun addFilterOpen(add: Boolean) {
        view.showProgresBar(true)
        view.clearListSites()
        interactor.addFilterOpen(add)
    }

    override fun addFilterScore(add: Boolean) {
        view.clearListSites()
        view.showProgresBar(true)
        interactor.addFilterScore(add)
    }

    override fun addFilterLocation(latitude: Double, longitude: Double, add: Boolean) {
        view.clearListSites()
        view.showProgresBar(true)
        interactor.addFilterLocation(latitude, longitude, add)
    }


    @Subscribe
    fun onEventThread(event: SitesEvents) {
        view.showProgresBar(false)
        when (event.type) {
            SitesEvents.ON_GET_SITES_SUCCESS -> {
                view.showFilters(View.VISIBLE)
                view.setResultsSearchs(event.any as List<Enterprise>)
                view.showButtonReload(View.GONE)
            }

            SitesEvents.ON_GET_SITES_ERROR -> {
                view.showMessagge(event.any.toString())
                view.showButtonReload(View.VISIBLE)
            }

        }
    }
}
