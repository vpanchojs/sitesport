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
        publicationView.showPbLoading()
        publicationInteractor.callPublication(pk)
    }

    @Subscribe
    override fun onEventPublicationThread(event: PublicationEvent) {

        publicationView.hidePbLoading()

        when (event.eventType) {

            PublicationEvent.SUCCESS -> {
                val p = event.eventObject as Publication
                if (!p.isOnline) {
                    publicationView.showSnackBar(event.eventMsg!!)
                }
                publicationView.setPublication(p)
            }

            PublicationEvent.ERROR -> {
                publicationView.showSnackBar(event.eventMsg!!)
            }

        }
    }
}