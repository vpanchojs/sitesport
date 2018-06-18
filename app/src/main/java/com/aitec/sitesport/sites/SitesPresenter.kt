package com.aitec.sitesport.sites

interface SitesPresenter {
    fun subscribe()
    fun unSubscribe()
    fun onGetSites()
    fun addFilterOpen(add: Boolean)
    fun addFilterScore(add: Boolean)
    fun addFilterLocation(latitude: Double, longitude: Double, add: Boolean)
}
