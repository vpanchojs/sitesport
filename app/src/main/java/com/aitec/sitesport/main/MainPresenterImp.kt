package com.aitec.sitesport.main

import com.aitec.sitesport.entities.Entreprise
import com.aitec.sitesport.entities.SearchCentersName
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
        view.showProgresBar(true)
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
                view.showProgresBar(false)
                var entrepriseList = event.any as List<Entreprise>
                if (entrepriseList.size > 0) {
                    view.setResultsSearchs(entrepriseList)
                } else {
                    view.showMessagge("No se encontro centros deportivos cercanos")
                }
                // view.setResultsSearchs(results.result)
            }
            MainEvents.ON_RESULTS_SEARCHS_ERROR -> {
                view.showMessagge(event.any as String)
            }

            MainEvents.ON_RESULTS_SEARCH_NAMES_SUCCESS -> {
                var searchCentersName = event.any as SearchCentersName

                if (searchCentersName.results.size > 0) {
                    view.setResultSearchsName(searchCentersName.results)
                } else {

                }
            }
            MainEvents.ON_RESULTS_SEARCH_NAMES_ERROR -> {

            }

        }
    }
}