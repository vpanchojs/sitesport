package com.aitec.sitesport.main.ui

import com.aitec.sitesport.entities.Entreprise

/**
 * Created by victor on 14/2/18.
 */
interface MainView {
    fun showMessagge(message: Any)
    fun setResultsSearchs(listUser: ArrayList<Entreprise>)
    fun showProgresBar(show: Boolean)
    fun clearSearchResults()
}