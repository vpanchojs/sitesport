package com.aitec.sitesport.sites

import com.aitec.sitesport.domain.RetrofitApi
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.sites.event.SitesEvents

class SitesRepositoryImp(var eventBus: EventBusInterface, var retrofitApi: RetrofitApi, var sharePreferencesApi: SharePreferencesApi) : SitesRepository {

    override fun onGetSites() {
        retrofitApi.getAllSites(object : onApiActionListener<List<Enterprise>> {
            override fun onSucces(response: List<Enterprise>) {
                postEvent(SitesEvents.ON_GET_SITES_SUCCESS, response!!)
            }

            override fun onError(error: Any?) {
                postEvent(SitesEvents.ON_GET_SITES_ERROR, error!!)
            }
        })
    }

    override fun onGetSites(parametros: HashMap<String, String>) {
        retrofitApi.getAllSites(parametros, object : onApiActionListener<List<Enterprise>> {
            override fun onSucces(response: List<Enterprise>) {
                postEvent(SitesEvents.ON_GET_SITES_SUCCESS, response!!)
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
}
