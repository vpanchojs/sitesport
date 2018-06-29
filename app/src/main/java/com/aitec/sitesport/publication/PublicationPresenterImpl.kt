package com.aitec.sitesport.publication

import com.aitec.sitesport.entities.Publication
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.publication.event.PublicationEvent
import com.aitec.sitesport.publication.ui.PublicationView
import org.greenrobot.eventbus.Subscribe

class PublicationPresenterImpl(val publicationView: PublicationView,
                               val publicationInteractor: PublicationInteractor,
                               val eventBusInterface: EventBusInterface) : PublicationPresenter {
    override fun register() {
        eventBusInterface.register(this)
    }

    override fun unregister() {
        eventBusInterface.unregister(this)
    }

    override fun callPublication(pk: String) {
        publicationView.showLoading()
        publicationInteractor.callPublication(pk)
    }

    @Subscribe
    override fun onEventPublicationThread(event: PublicationEvent) {

        publicationView.hideLoading(event.eventMsg)

        when (event.eventType) {

            PublicationEvent.SUCCESS -> {
                publicationView.setPublication(event.eventObject as Publication)
            }

            PublicationEvent.ERROR -> { }

        }
    }
}