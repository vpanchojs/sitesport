package com.aitec.sitesport.sites

interface SitesInteractor {
    fun onGetSites()
    fun addFilterOpen(add: Boolean)
    fun addFilterScore(add: Boolean)
    fun addFilterLocation(ubicacion: String, add: Boolean)
}
