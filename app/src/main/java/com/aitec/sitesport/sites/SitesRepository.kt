package com.aitec.sitesport.sites

interface SitesRepository {
    fun onGetSites()
    fun onGetSites(parametros: HashMap<String, String>)
    fun onGetSitesScore(parametros: HashMap<String, String>)
    fun onGetSitesOpen(parametros: HashMap<String, String>)
    fun onGetSitesLocation(parametros: HashMap<String, String>)

}
