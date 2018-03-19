package com.aitec.sitesport.profile

/**
 * Created by Yavac on 12/3/2018.
 */
interface ProfileRepository {
    fun getProfile(pk : String)
    fun stopRequests()
}