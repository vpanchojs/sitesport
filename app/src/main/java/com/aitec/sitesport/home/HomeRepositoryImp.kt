package com.aitec.sitesport.home

import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.domain.listeners.RealTimeListener
import com.aitec.sitesport.entities.Publication
import com.aitec.sitesport.home.events.HomeEvents
import com.aitec.sitesport.lib.base.EventBusInterface

/**
 * Created by Jhony on 28 may 2018.
 */


class HomeRepositoryImp(var eventBus: EventBusInterface, var sharePreferencesApi: SharePreferencesApi, var firebaseApi: FirebaseApi) : HomeRepository {

    override fun getHome() {
        firebaseApi.getHome(object : RealTimeListener<Publication> {

            override fun emptyNode(msg: Any) {
                postEvent(HomeEvents.EMPTY, msg)
            }

            override fun addDocument(response: Publication) {
                postEvent(HomeEvents.ON_ADD_PUBLISH, response)
            }

            override fun removeDocument(response: Publication) {
                postEvent(HomeEvents.ON_REMOVE_PUBLISH, response)
            }

            override fun updateDocument(response: Publication) {
                postEvent(HomeEvents.ON_UPDATE_PUBLISH, response)
            }

            override fun omError(error: Any) {
                postEvent(HomeEvents.ON_ERROR_PUBLISH, error)
            }

            /*override fun onSucces(response: Publication) {
                postEvent(HomeEvents.ON_GET_HOME,response)
            }

            override fun onError(error: Any?) {
                if (error != null) {
                    postEvent(HomeEvents.ON_GET_HOME_ERROR, error)
                }
            }*/
        })

    }

    override fun remove() {
        firebaseApi.removelistener()
    }

    private fun postEvent(type: Int, any: Any) {
        val event = HomeEvents(type, any)
        eventBus.post(event)
    }
}



