package com.aitec.sitesport.profileEnterprise

import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.profileEnterprise.event.ProfileEvent

/**
 * Created by Yavac on 12/3/2018.
 */
interface ProfilePresenter {
    fun register()
    fun unregister()
    fun getBasicProfile(idEnterprise : String)
    fun getTableTimeProfile(idEnterprise : String)
    fun getCourtsProfile(idEnterprise : String)
    fun getServicesProfile(idEnterprise : String)
    fun getContactsProfile(idEnterprise : String)
    fun onEventProfileThread(profileEvent: ProfileEvent)

}