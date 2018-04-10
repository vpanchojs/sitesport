package com.aitec.sitesport.mapSites.ui

import com.aitec.sitesport.entities.enterprise.Enterprise

/**
 * Created by victor on 14/2/18.
 */
interface MapSitesView {
    fun showMessagge(message: Any)
    fun setResultsSearchs(entrepriseList: List<Enterprise>)
    fun showProgresBar(show: Boolean)
    fun showProgresBarResultsMapVisible(show: Boolean)
    fun clearSearchResultsName()
    fun clearSearchResultsVisible()
    fun setResultSearchsName(results: List<Enterprise>)
    fun noneResultSearchsName(message: Any, show: Boolean)
    fun noneResultCenterVisible(s: Any)
    fun setInfoHeaderBottomSheet(title: Any, subtitle: Any)
    fun showNoneResulstEntrepiseVisible(show: Boolean)
    fun hideButtonProfileEntrepise()

}