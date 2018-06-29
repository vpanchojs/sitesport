package com.aitec.sitesport.publication

import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.Publication
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.publication.event.PublicationEvent

class PublicationRepositoryImpl(val firebaseApi: FirebaseApi,
                                val eventBusInterface: EventBusInterface,
                                val sharePreferencesApi: SharePreferencesApi) : PublicationRepository{
    override fun callPublication(pk: String) {
        firebaseApi.getPublication(pk, object: onApiActionListener<Publication>{
            override fun onSucces(response: Publication) {
                var msg: String? = null
                if (!response.isOnline) msg = MSG_ERROR_CONNECTION
                postEvent(PublicationEvent.SUCCESS, msg, response)
            }

            override fun onError(error: Any?) {
                postEvent(PublicationEvent.ERROR, error.toString(), null)
            }

        })
    }

    private fun postEvent(type: Int, any: Any?, eventObject: Any?) {
        val event = PublicationEvent(type, any.toString(), eventObject)
        eventBusInterface.post(event)
    }

    companion object {
        const val MSG_ERROR_CONNECTION = "Problemas de conexión"
    }

}