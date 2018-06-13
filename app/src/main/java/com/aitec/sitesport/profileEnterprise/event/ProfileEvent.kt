package com.aitec.sitesport.profileEnterprise.event

import com.aitec.sitesport.entities.enterprise.Enterprise

/**
 * Created by Yavac on 16/3/2018.
 */
class ProfileEvent(var eventType: Int, var sectionView: Int, var eventMsg: String?, var eventObject : Any?) {

    companion object {
        const val SUCCESS = 1
        const val ERROR = 0
    }

}