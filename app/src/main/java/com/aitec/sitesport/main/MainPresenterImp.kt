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
        view.showProgresBarResultsMapVisible(true)
        view.hideButtonProfileEntrepise()
        view.setInfoHeaderBottomSheet("Centro Deportivos", "Buscando centros deportivos")
        interactor.onGetCenterSportVisible(latSouth, latNorth, lonWest, lonEast, latMe, lngMe)
    }

    override fun getSearchName(query: String) {
        if (interactor.getSearchName(query)) {
            view.showProgresBar(true)
            view.clearSearchResultsName()
        } else {
            view.showProgresBar(false)
            view.clearSearchResultsName()
        }
    }

    override fun stopSearchName() {
        interactor.stopSearchName()
    }

    override fun stopSearchVisibility() {
        interactor.stopSearchVisibility()
    }

    @Subscribe
    override fun onEventMainThread(event: MainEvents) {
        view.showProgresBar(false)
        view.showProgresBarResultsMapVisible(false)
        when (event.type) {
            MainEvents.ON_RESULTS_SEARCHS_SUCCESS -> {
                var entrepriseList = event.any as List<Enterprise>
                if (entrepriseList.size > 0) {
                    view.clearSearchResultsVisible()
                    view.setResultsSearchs(entrepriseList)
                    view.showNoneResulstEntrepiseVisible(false)
                } else {
                    view.setInfoHeaderBottomSheet("Centro Deportivos", "Sin Resultados")
                    view.clearSearchResultsVisible()
                    view.showNoneResulstEntrepiseVisible(true)
                }
            }
            MainEvents.ON_RESULTS_SEARCHS_ERROR -> {
                view.showMessagge(event.any as String)
            }

            MainEvents.ON_RESULTS_SEARCH_NAMES_SUCCESS -> {
                var searchCentersName = event.any as SearchCentersName

                if (searchCentersName.results.size > 0) {
                    view.noneResultSearchsName(Any(), false)
                    view.setResultSearchsName(searchCentersName.results)
                } else {
                    view.noneResultSearchsName(Any(), true)
                }
            }
            MainEvents.ON_RESULTS_SEARCH_NAMES_ERROR -> {
                view.showMessagge(event.any as String)
            }

        }
    }
}