package com.aitec.sitesport.home

import com.aitec.sitesport.home.events.HomeEvents

/**
 * Created by Jhony on 28 may 2018.
 */






interface HomePresenter {

    fun getHome()
    fun Suscribe()
    fun onSuscribe()
    fun onEvents(events: HomeEvents)
}