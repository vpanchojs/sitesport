package com.aitec.sitesport.mapSites

import android.view.View
import com.aitec.sitesport.entities.SearchCentersName
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.mapSites.events.MapSitesEvents
import com.aitec.sitesport.mapSites.ui.MapSitesView

import org.greenrobot.eventbus.Subscribe

class MapSitesPresenterImp(var eventBus: EventBusInterface, var view: MapSitesView, var interactor: MapSitesInteractor) : MapSitesPresenter {

    override fun onResume() {
        eventBus.register(this)

    }

    override fun onPause() {
        eventBus.unregister(this)
    }

    override fun onGetCenterSportVisible(latSouth: Double, latNorth: Double, lonWest: Double, lonEast: Double, latMe: Double, lngMe: Double) {
        view.showProgresBarResultsMapVisible(true)
        view.hideButtonProfileEntrepise()
        view.setInfoHeaderBottomSheet("Sitios Deportivos", "Buscando sitios deportivos")
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

    override fun onGetAllCenterSport() {
        interactor.onGetAllCenterSport()
    }


    fun setSites(sites: List<Enterprise>) {
        view.clearSearchResultsVisible()

        if (sites.isNotEmpty()) {
            view.setResultsSearchs(sites)
            view.showNoneResulstEntrepiseVisible(false)

        } else {
            view.showNoneResulstEntrepiseVisible(true)
        }

    }

    @Subscribe
    override fun onEventmapSitesThread(event: MapSitesEvents) {
        view.showProgresBar(false)
        view.showProgresBarResultsMapVisible(false)
        when (event.type) {
            MapSitesEvents.ON_RESULTS_SEARCHS_SUCCESS -> {

                val data = event.any as Pair<List<Enterprise>, Boolean>

                if (data.second) {
                    view.setInfoHeaderBottomSheet("Problemas de conexión", "Sitios almacenados en tu dispositivo")
                    view.showButtonReload(View.VISIBLE)
                } else {
                    view.setInfoHeaderBottomSheet("Sitios Deportivos", " ${data.first.size} encontrados")
                    view.showButtonReload(View.GONE)
                }

                setSites(data.first)

            }
            MapSitesEvents.ON_RESULTS_SEARCHS_ERROR -> {
                view.showMessagge(event.any as String)
            }

            MapSitesEvents.ON_RESULTS_SEARCH_NAMES_SUCCESS -> {
                var searchCentersName = event.any as SearchCentersName

                if (searchCentersName.results.size > 0) {
                    view.noneResultSearchsName(Any(), false)
                    view.setResultSearchsName(searchCentersName.results)
                } else {
                    view.noneResultSearchsName(Any(), true)
                }
            }
            MapSitesEvents.ON_RESULTS_SEARCH_NAMES_ERROR -> {
                view.showMessagge(event.any as String)
            }

        }
    }
}