package com.aitec.sitesport.main.ui

import com.aitec.sitesport.entities.enterprise.Enterprise

/**
 * Created by victor on 14/2/18.
 */
interface MainView {

    fun showMessagge(message: Any)
    fun setResultsSearchs(listUser: ArrayList<Enterprise>)
    fun showProgresBar(show: Boolean)
    fun clearSearchResults()
    fun navigationWelcome()

}