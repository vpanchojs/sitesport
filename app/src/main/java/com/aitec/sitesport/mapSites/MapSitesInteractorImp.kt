package com.aitec.sitesport.mapSites

import android.util.Log

/**
 * Created by victor on 27/1/18.
 */
class MapSitesInteractorImp(var repository: MapSitesRepository) : MapSitesInteractor {
    override fun getSearchName(query: String): Boolean {
        if (query.length > 0) {
            repository.getSearchName(query)
            return true
        } else {
            Log.e("mapSitesI", "query muy pequeño")
            return false
        }
    }

    override fun onGetCenterSportVisible(latSouth: Double, latNorth: Double, lonWest: Double, lonEast: Double, latMe: Double, lngMe: Double) {
        repository.onGetCenterSportVisible(latSouth, latNorth, lonWest, lonEast, latMe, lngMe)
    }

    override fun stopSearchName() {
        repository.stopSearchName()
    }

    override fun stopSearchVisibility() {
        repository.stopSearchVisibility()
    }
}