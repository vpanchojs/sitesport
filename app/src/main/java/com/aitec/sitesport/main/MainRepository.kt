package com.aitec.sitesport.main

/**
 * Created by victor on 27/1/18.
 */
interface MainRepository {
    fun getSearchUserEntrepise(query: String)
    fun stopSearchUserEntrepise()
    fun onGetCenterSportVisible(latSouth: Double, latNorth: Double, lonWest: Double, lonEast: Double, latMe: Double, lngMe: Double)
}