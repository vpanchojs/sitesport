package com.aitec.sitesport.profile

import com.aitec.sitesport.entities.enterprise.Enterprise

/**
 * Created by Yavac on 16/3/2018.
 */
class ProfileInteractorImpl(val profileRepository : ProfileRepository) : ProfileInteractor {

    override fun getProfile(enterprise: Enterprise?) {
        profileRepository.getProfile(enterprise)
    }

}