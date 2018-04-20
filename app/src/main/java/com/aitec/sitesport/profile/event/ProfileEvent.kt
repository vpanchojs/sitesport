package com.aitec.sitesport.profile.event

import com.aitec.sitesport.entities.enterprise.Enterprise

/**
 * Created by Yavac on 16/3/2018.
 */
class ProfileEvent(var eventType: Int, var eventMsg: String, var eventObject : Any?) {

    companion object {
        const val SUCCESS_PROFILE = 1
        const val ERROR_PROFILE = -1
    }

}