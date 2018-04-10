package com.aitec.sitesport.profile

import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.profile.event.ProfileEvent

/**
 * Created by Yavac on 12/3/2018.
 */
interface ProfilePresenter {
    fun onResume()
    fun onPause()
    fun onDestroy()
    fun getProfile(urlDetail : String)
    fun onEventProfileThread(profileEvent: ProfileEvent)

}