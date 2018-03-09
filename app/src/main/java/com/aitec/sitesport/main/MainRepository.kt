package com.aitec.sitesport.main

/**
 * Created by victor on 27/1/18.
 */
interface MainRepository {
    fun getSearchUserEntrepise(query: String)
    fun stopSearchUserEntrepise()
}