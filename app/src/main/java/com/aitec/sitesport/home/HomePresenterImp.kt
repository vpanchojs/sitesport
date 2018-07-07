package com.aitec.sitesport.home

import android.util.Log
import com.aitec.sitesport.entities.Publication
import com.aitec.sitesport.home.events.HomeEvents
import com.aitec.sitesport.home.ui.HomeView
import com.aitec.sitesport.lib.base.EventBusInterface
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Jhony on 28 may 2018.
 */

class HomePresenterImp(var eventBus: EventBusInterface, var view: HomeView, var interactor: HomeInteractor) : HomePresenter {

    override fun remove() {
        interactor.remove()
    }

    override fun getHome() {
        view.showProgressBar(true)
        view.clearListPublications()
        interactor.getHome()


    }

    override fun Suscribe() {
        eventBus.register(this)
    }

    override fun onSuscribe() {
        eventBus.unregister(this)

    }


    @Subscribe
    override fun onEvents(events: HomeEvents) {

        view.showProgressBar(false)
        when (events.type) {

            HomeEvents.ON_ADD_PUBLISH -> {
                view.addPublication(events.any as Publication)
            }
            HomeEvents.ON_UPDATE_PUBLISH -> {
                view.updatePublication(events.any as Publication)
            }
            HomeEvents.ON_REMOVE_PUBLISH -> {
                view.removePublication(events.any as Publication)
            }

            HomeEvents.ON_ERROR_PUBLISH -> {
                view.showMessage(events.any.toString())
            }

            HomeEvents.EMPTY -> {
                view.showInfo(events.any.toString())
            }

        }
    }

}