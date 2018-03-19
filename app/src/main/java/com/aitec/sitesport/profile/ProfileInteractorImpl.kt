package com.aitec.sitesport.profile

/**
 * Created by Yavac on 16/3/2018.
 */
class ProfileInteractorImpl(val profileRepository : ProfileRepository) : ProfileInteractor {

    override fun getProfile(pk: String) {
        profileRepository.getProfile(pk)
    }

}