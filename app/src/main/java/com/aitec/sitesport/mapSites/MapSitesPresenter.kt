package com.aitec.sitesport.mapSites

import com.aitec.sitesport.mapSites.events.MapSitesEvents

/**
 * Created by victor on 27/1/18.
 */
interface MapSitesPresenter {

    fun onResume()

    fun onPause()

    fun onGetCenterSportVisible(latSouth: Double, latNorth: Double, lonWest: Double, lonEast: Double, latMe: Double, lngMe: Double)

    fun getSearchName(query: String)

    fun stopSearchName()

    fun stopSearchVisibility()

    fun onEventmapSitesThread(event: MapSitesEvents)

    fun onGetAllCenterSport()

}