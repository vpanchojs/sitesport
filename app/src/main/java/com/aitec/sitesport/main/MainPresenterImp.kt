package com.aitec.sitesport.main

import com.aitec.sitesport.entities.SearchCentersName
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.main.events.MainEvents
import com.aitec.sitesport.main.ui.MainView
import org.greenrobot.eventbus.Subscribe

class MainPresenterImp(var eventBus: EventBusInterface, var view: MainView, var interactor: MainInteractor) : MainPresenter {

    override fun onResume() {
        eventBus.register(this)

    }

    override fun onPause() {
        eventBus.unregister(this)
    }

    override fun onGetCenterSportVisible(latSouth: Double, latNorth: Double, lonWest: Double, lonEast: Double, latMe: Double, lngMe: Double) {
        /*
         view.showProgresBarResultsMapVisible(true)
         view.hideButtonProfileEntrepise()
         view.setInfoHeaderBottomSheet("Centro Deportivos", "Buscando centros deportivos")
         interactor.onGetCenterSportVisible(latSouth, latNorth, lonWest, lonEast, latMe, lngMe)
         */
    }

    override fun getSearchName(query: String) {
        if (interactor.getSearchName(query)) {
            view.showProgresBar(true)
            view.clearSearchResults()
        } else {
            view.showProgresBar(false)
            view.clearSearchResults()
        }
    }

    override fun stopSearchName() {
        interactor.stopSearchName()
    }

    override fun stopSearchVisibility() {
        interactor.stopSearchVisibility()
    }

    override fun initfirstRun() {
        interactor.initfirstRun()
    }

    @Subscribe
    override fun onEventMainThread(event: MainEvents) {

        view.showProgresBar(false)
        when (event.type) {
            MainEvents.ON_RESULTS_SEARCH_NAMES_SUCCESS -> {
                var results = event.any as SearchCentersName
                view.setResultsSearchs(results.results as ArrayList<Enterprise>)
            }

            MainEvents.ON_RESULTS_SEARCH_NAMES_ERROR -> {
                view.showMessagge(event.any as String)
            }

            MainEvents.ON_LAUCH_WELCOME_SUCCESS -> {
                view.navigationWelcome()
            }

        }
    }
}