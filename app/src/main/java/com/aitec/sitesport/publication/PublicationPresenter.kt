package com.aitec.sitesport.publication

import com.aitec.sitesport.publication.event.PublicationEvent

interface PublicationPresenter {

    fun register()
    fun unregister()
    fun callPublication(pk: String)
    fun onEventPublicationThread(event: PublicationEvent)
}