package com.aitec.sitesport.profileEnterprise

import com.aitec.sitesport.entities.enterprise.Enterprise

/**
 * Created by Yavac on 12/3/2018.
 */
interface ProfileRepository {
    fun getBasicProfile(idEnterprise : String)
    fun getTableTime(idEnterprise: String)
    fun getCourts(idEnterprise: String)
    fun getServices(idEnterprise: String)
    fun getContacts(idEnterprise: String)
    fun stopRequests()
}