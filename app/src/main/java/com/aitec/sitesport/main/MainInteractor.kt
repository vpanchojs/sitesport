package com.aitec.sitesport.main

/**
 * Created by victor on 27/1/18.
 */
interface MainInteractor {
    fun getSearchName(query: String): Boolean
    fun stopSearchName()
    fun onGetCenterSportVisible(latSouth: Double, latNorth: Double, lonWest: Double, lonEast: Double, latMe: Double, lngMe: Double)
    fun stopSearchVisibility()
    fun initfirstRun()

}