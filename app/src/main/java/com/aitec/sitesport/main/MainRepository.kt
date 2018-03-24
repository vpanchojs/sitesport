package com.aitec.sitesport.main

/**
 * Created by victor on 27/1/18.
 */
interface MainRepository {
    fun getSearchName(query: String)
    fun stopSearchName()
    fun onGetCenterSportVisible(latSouth: Double, latNorth: Double, lonWest: Double, lonEast: Double, latMe: Double, lngMe: Double)
    fun stopSearchVisibility()
}