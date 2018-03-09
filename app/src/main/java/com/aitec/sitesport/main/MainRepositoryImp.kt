package com.aitec.sitesport.main

import android.util.Log
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.domain.VolleyApi
import com.aitec.sitesport.domain.listeners.onVolleyApiActionListener
import com.aitec.sitesport.domain.util.MapperError
import com.aitec.sitesport.domain.util.MapperResponse
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.main.events.MainEvents
import org.json.JSONObject

/**
 * Created by victor on 27/1/18.
 */
class MainRepositoryImp(var eventBus: EventBusInterface, var volleyApi: VolleyApi, var sharePreferencesApi: SharePreferencesApi) : MainRepository {

    override fun getSearchUserEntrepise(query: String) {

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
    }

    override fun stopSearchUserEntrepise() {
        volleyApi.removeAllRequestSearchUserorEntrepise()
    }

    private fun postEvent(type: Int, message: String, any: Any) {
        var event = MainEvents(type, any, message)
        eventBus.post(event)
    }
}