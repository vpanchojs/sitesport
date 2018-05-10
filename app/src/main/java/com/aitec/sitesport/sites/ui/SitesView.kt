package com.aitec.sitesport.sites.ui

import com.aitec.sitesport.entities.enterprise.Enterprise

interface SitesView {
    fun showMessagge(message: Any)
    fun setResultsSearchs(listUser: List<Enterprise>)
    fun showProgresBar(show: Boolean)
    fun clearSearchResults()
    fun clearListSites()
    fun showButtonReload(visible: Int)
}