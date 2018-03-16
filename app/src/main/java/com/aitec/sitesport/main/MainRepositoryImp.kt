package com.aitec.sitesport.main

import com.aitec.sitesport.domain.RetrofitApi
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.domain.listeners.onVolleyApiActionListener
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.main.events.MainEvents

/**
 * Created by victor on 27/1/18.
 */
class MainRepositoryImp(var eventBus: EventBusInterface, var retrofitApi: RetrofitApi, var sharePreferencesApi: SharePreferencesApi) : MainRepository {

    override fun getSearchUserEntrepise(query: String) {
/*
        volleyApi.onSearchUserorEntrepise(query, sharePreferencesApi.getTokenAccess(), object : onVolleyApiActionListener {

            override fun onSucces(response: Any?) {
                val responseObject = response as JSONObject
                Log.e("MainR", responseObject.toString())
                // postEvent(MainEvents.ON_RESULTS_SEARCHS_SUCCESS, "", MapperResponse.getResultSearch(responseObject))
            }

            override fun onError(error: Any?) {
                postEvent(MainEvents.ON_RESULTS_SEARCHS_ERROR, MapperError.getErrorResponse(error!!), Any())
            }
        })
        */
    }

    override fun onGetCenterSportVisible(latSouth: Double, latNorth: Double, lonWest: Double, lonEast: Double, latMe: Double, lngMe: Double) {
        retrofitApi.getCenterSport(latSouth, latNorth, lonWest, lonEast, latMe, lngMe, object : onVolleyApiActionListener {
            override fun onSucces(response: Any?) {
                postEvent(MainEvents.ON_RESULTS_SEARCHS_SUCCESS, response!!)
            }

            override fun onError(error: Any?) {

            }
        })
    }

    override fun stopSearchUserEntrepise() {
        //   volleyApi.removeAllRequestSearchUserorEntrepise()
    }

    private fun postEvent(type: Int, any: Any) {
        var event = MainEvents(type, any)
        eventBus.post(event)
    }
}