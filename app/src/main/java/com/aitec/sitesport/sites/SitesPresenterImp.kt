package com.aitec.sitesport.sites

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
        interactor.onGetSites()
    }

    @Subscribe
    fun onEventThread(event: SitesEvents) {
        when (event.type) {
            SitesEvents.ON_GET_SITES_SUCCESS -> {
                view.setResultsSearchs(event.any as List<Enterprise>)
            }

            SitesEvents.ON_GET_SITES_ERROR -> {

            }

        }
    }
}
