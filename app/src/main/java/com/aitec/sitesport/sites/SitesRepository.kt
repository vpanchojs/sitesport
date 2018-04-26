package com.aitec.sitesport.sites

interface SitesRepository {
    fun onGetSites()
    fun onGetSites(parametros: HashMap<String, String>)
}
