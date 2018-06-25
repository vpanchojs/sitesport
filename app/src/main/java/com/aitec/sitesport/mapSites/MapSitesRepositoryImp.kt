package com.aitec.sitesport.mapSites

import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.SearchCentersName
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.mapSites.events.MapSitesEvents

/**
 * Created by victor on 27/1/18.
 */
class MapSitesRepositoryImp(var eventBus: EventBusInterface, var firebaseApi: FirebaseApi) : MapSitesRepository {

    override fun getSearchName(query: String) {

        firebaseApi.getSearchName(query, object : onApiActionListener<SearchCentersName> {
            override fun onSucces(response: SearchCentersName) {
                postEvent(MapSitesEvents.ON_RESULTS_SEARCH_NAMES_SUCCESS, response)
            }

            override fun onError(error: Any?) {
                postEvent(MapSitesEvents.ON_RESULTS_SEARCH_NAMES_ERROR, error!!)
            }
        })

    }

    override fun onGetCenterSportVisible(latSouth: Double, latNorth: Double, lonWest: Double, lonEast: Double, latMe: Double, lngMe: Double) {
        /*
        retrofitApi.getCenterSport(latSouth, latNorth, lonWest, lonEast, latMe, lngMe, object : onApiActionListener<List<Enterprise>> {
            override fun onSucces(response: List<Enterprise>) {
                postEvent(MapSitesEvents.ON_RESULTS_SEARCHS_SUCCESS, response!!)
            }

            override fun onError(error: Any?) {
                postEvent(MapSitesEvents.ON_RESULTS_SEARCHS_ERROR, error!!)
            }
        })
        */
    }

    override fun stopSearchName() {
        firebaseApi.deleteRequestSearchName()
    }

    override fun stopSearchVisibility() {
        //retrofitApi.deleteRequestGetCenterSport()
    }


    private fun postEvent(type: Int, any: Any) {
        var event = MapSitesEvents(type, any)
        eventBus.post(event)
    }

    override fun onGetAllCenterSport() {
        firebaseApi.getAllSites(object : onApiActionListener<List<Enterprise>> {
            override fun onSucces(response: List<Enterprise>) {
                postEvent(MapSitesEvents.ON_RESULTS_SEARCHS_SUCCESS, response)
            }

            override fun onError(error: Any?) {
                postEvent(MapSitesEvents.ON_RESULTS_SEARCHS_ERROR, error!!)
            }
        })
    }

}