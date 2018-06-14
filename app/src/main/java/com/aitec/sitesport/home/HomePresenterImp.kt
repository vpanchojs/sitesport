package com.aitec.sitesport.home

import android.util.Log
import com.aitec.sitesport.entities.Publications
import com.aitec.sitesport.home.events.HomeEvents
import com.aitec.sitesport.home.ui.HomeView
import com.aitec.sitesport.lib.base.EventBusInterface
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Jhony on 28 may 2018.
 */




class HomePresenterImp(var eventBus: EventBusInterface, var view: HomeView, var interactor: HomeInteractor) : HomePresenter {
    override fun getHome() {
        view.showProgresBar(true)
        interactor.getHome()

    }

    override fun Suscribe() {
        eventBus.register(this)
    }

    override fun onSuscribe() {
        eventBus.unregister(this)

    }

    @Subscribe
    override fun onEvents(events: HomeEvents){

        view.showProgresBar(false)
        when (events.type ){

            HomeEvents.ON_ADD_PUBLISH->{
                Log.e("llega","")
                view.addPublicacion(events.any as Publications)

            }
            HomeEvents.ON_UPDATE_PUBLISH->{

                Log.e("update","")
                view.updatePublicacion(events.any as Publications)
            }
            HomeEvents.ON_REMOVE_PUBLISH->{

                Log.e("remove","")
                view.removePublicacion(events.any as Publications)
            }

            HomeEvents.ON_ERROR_PUBLISH->{
                view.showMessagge(events.any.toString())

            }

        }
    }

}