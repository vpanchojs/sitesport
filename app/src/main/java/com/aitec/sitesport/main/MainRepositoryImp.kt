package com.aitec.sitesport.main

import com.aitec.sitesport.domain.RetrofitApi
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.SearchCentersName
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.main.events.MainEvents

/**
 * Created by victor on 27/1/18.
 */
class MainRepositoryImp(var eventBus: EventBusInterface, var retrofitApi: RetrofitApi, var sharePreferencesApi: SharePreferencesApi) : MainRepository {

    override fun getSearchName(query: String) {
        retrofitApi.onSearchNameCenterSport(query, object : onApiActionListener<SearchCentersName> {
            override fun onSucces(response: SearchCentersName) {
                postEvent(MainEvents.ON_RESULTS_SEARCH_NAMES_SUCCESS, response)
            }

            override fun onError(error: Any?) {
                postEvent(MainEvents.ON_RESULTS_SEARCH_NAMES_ERROR, error!!)
            }
        })

    }

    override fun onGetCenterSportVisible(latSouth: Double, latNorth: Double, lonWest: Double, lonEast: Double, latMe: Double, lngMe: Double) {
        retrofitApi.getCenterSport(latSouth, latNorth, lonWest, lonEast, latMe, lngMe, object : onApiActionListener<List<Enterprise>> {
            override fun onSucces(response: List<Enterprise>) {
                postEvent(MainEvents.ON_RESULTS_SEARCHS_SUCCESS, response)
            }

            override fun onError(error: Any?) {
                postEvent(MainEvents.ON_RESULTS_SEARCHS_ERROR, error!!)
            }
        })
    }

    override fun stopSearchName() {
        retrofitApi.deleteRequestSearchName()
    }

    override fun stopSearchVisibility() {
        retrofitApi.deleteRequestGetCenterSport()
    }


    override fun initfirstRun() {
        sharePreferencesApi.getFirstRun(object : onApiActionListener<Any> {
            override fun onSucces(response: Any) {
                postEvent(MainEvents.ON_LAUCH_WELCOME_SUCCESS, response!!)
            }

            override fun onError(error: Any?) {

            }
        })
    }

    private fun postEvent(type: Int, any: Any) {
        var event = MainEvents(type, any)
        eventBus.post(event)
    }
}