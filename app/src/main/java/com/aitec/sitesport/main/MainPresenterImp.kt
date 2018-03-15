package com.aitec.sitesport.main

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
        interactor.onGetCenterSportVisible(latSouth, latNorth, lonWest, lonEast, latMe, lngMe)
    }

    override fun getSearchUserEntrepise(query: String) {
        if (interactor.getSearchUserEntrepise(query)) {
            view.showProgresBar(true)
            view.clearSearchResults()
        } else {
            view.showProgresBar(false)
            view.clearSearchResults()
            view.showMessagge("query invalido")
        }
    }

    override fun stopSearchUserEntrepise() {
        interactor.stopSearchUserEntrepise()
    }

    @Subscribe
    override fun onEventMainThread(event: MainEvents) {
        view.showProgresBar(false)
        when (event.type) {
            MainEvents.ON_RESULTS_SEARCHS_SUCCESS -> {
                // var results = event.any as SearchUserOrEntreprise
                // view.setResultsSearchs(results.result)
            }
            MainEvents.ON_RESULTS_SEARCHS_ERROR -> {
                view.showMessagge(event.message)
            }

        }
    }
}