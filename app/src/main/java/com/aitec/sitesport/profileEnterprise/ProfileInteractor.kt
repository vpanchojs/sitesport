package com.aitec.sitesport.profileEnterprise

/**
 * Created by Yavac on 12/3/2018.
 */
interface ProfileInteractor {
    fun getBasicProfile(idEnterprise: String)
    fun getTableTime(idEnterprise: String)
    fun getCourts(idEnterprise: String)
    fun getServices(idEnterprise: String)
    fun getContacts(idEnterprise: String)
    fun getLike(idUser: String, idEnterprise: String)
    fun toggleLike(idUser: String, idEnterprise: String, isQualified: Boolean)
    fun isAuthenticated()
}