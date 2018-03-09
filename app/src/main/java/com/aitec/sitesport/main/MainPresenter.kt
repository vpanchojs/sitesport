package com.aitec.sitesport.main

import com.aitec.sitesport.main.events.MainEvents

/**
 * Created by victor on 27/1/18.
 */
interface MainPresenter {

    fun onResume()

    fun onPause()

    fun getSearchUserEntrepise(query: String)

    fun stopSearchUserEntrepise()

    fun onEventMainThread(event: MainEvents)
}