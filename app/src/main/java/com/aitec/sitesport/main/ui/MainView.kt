package com.aitec.sitesport.main.ui

import com.aitec.sitesport.entities.Entreprise

/**
 * Created by victor on 14/2/18.
 */
interface MainView {
    fun showMessagge(message: Any)
    fun setResultsSearchs(entrepriseList: List<Entreprise>)
    fun showProgresBar(show: Boolean)
    fun showProgresBarResultsMapVisible(show: Boolean)
    fun clearSearchResultsName()
    fun clearSearchResultsVisible()
    fun setResultSearchsName(results: List<Entreprise>)
    fun noneResultSearchsName(message: Any, show: Boolean)
    fun noneResultCenterVisible(s: Any)
    fun setInfoHeaderBottomSheet(title: Any, subtitle: Any)
    fun showNoneResulstEntrepiseVisible(show: Boolean)
    fun hideButtonProfileEntrepise()

}