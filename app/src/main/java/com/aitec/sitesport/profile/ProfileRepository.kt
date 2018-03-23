package com.aitec.sitesport.profile

import com.aitec.sitesport.entities.enterprise.Enterprise

/**
 * Created by Yavac on 12/3/2018.
 */
interface ProfileRepository {
    fun getProfile(enterprise : Enterprise?)
    fun stopRequests()
}