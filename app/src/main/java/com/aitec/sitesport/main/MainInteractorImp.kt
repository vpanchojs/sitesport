package com.aitec.sitesport.main

import android.util.Log

/**
 * Created by victor on 27/1/18.
 */
class MainInteractorImp(var repository: MainRepository) : MainInteractor {
    override fun getSearchName(query: String): Boolean {
        if (query.length > 0) {
            repository.getSearchName(query)
            return true
        } else {
            Log.e("mainI", "query muy pequeño")
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

    override fun initfirstRun() {
        repository.initfirstRun()
    }
}