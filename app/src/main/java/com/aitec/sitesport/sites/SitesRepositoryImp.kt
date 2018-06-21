package com.aitec.sitesport.sites

import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.sites.event.SitesEvents

class SitesRepositoryImp(var eventBus: EventBusInterface, var firebaseApi: FirebaseApi) : SitesRepository {

    override fun onGetSites() {
        firebaseApi.getAllSites(HashMap(), object : onApiActionListener<List<Enterprise>> {
            override fun onSucces(response: List<Enterprise>) {
                postEvent(SitesEvents.ON_GET_SITES_SUCCESS, response)
            }

            override fun onError(error: Any?) {
                postEvent(SitesEvents.ON_GET_SITES_ERROR, error!!)
            }
        })
    }

    override fun onGetSites(parametros: HashMap<String, String>) {
        firebaseApi.getAllSites(parametros, object : onApiActionListener<List<Enterprise>> {
            override fun onSucces(response: List<Enterprise>) {
                postEvent(SitesEvents.ON_GET_SITES_SUCCESS, response)
            }

            override fun onError(error: Any?) {
                postEvent(SitesEvents.ON_GET_SITES_ERROR, error!!)
            }
        })
    }

    private fun postEvent(type: Int, any: Any) {
        var event = SitesEvents(type, any)
        eventBus.post(event)
    }

    override fun onGetSitesScore(parametros: HashMap<String, String>) {
        firebaseApi.getSitesScore(parametros, object : onApiActionListener<List<Enterprise>> {
            override fun onSucces(response: List<Enterprise>) {
                postEvent(SitesEvents.ON_GET_SITES_SUCCESS, response)
            }

            override fun onError(error: Any?) {
                postEvent(SitesEvents.ON_GET_SITES_ERROR, error!!)
            }
        })
    }

    override fun onGetSitesOpen(parametros: HashMap<String, String>) {

        firebaseApi.getSitesOpen(parametros, object : onApiActionListener<List<Enterprise>> {
            override fun onSucces(response: List<Enterprise>) {
                postEvent(SitesEvents.ON_GET_SITES_SUCCESS, response)
            }

            override fun onError(error: Any?) {
                postEvent(SitesEvents.ON_GET_SITES_ERROR, error!!)
            }
        })
    }

    override fun onGetSitesLocation(parametros: Map<String, Any>) {
        firebaseApi.getSitesLocation(parametros, object : onApiActionListener<List<Enterprise>> {
            override fun onSucces(response: List<Enterprise>) {

            }

            override fun onError(error: Any?) {

            }
        })
    }
}
