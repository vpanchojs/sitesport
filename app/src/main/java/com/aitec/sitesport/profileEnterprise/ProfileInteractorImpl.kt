package com.aitec.sitesport.profileEnterprise

/**
 * Created by Yavac on 16/3/2018.
 */
class ProfileInteractorImpl(val profileRepository : ProfileRepository) : ProfileInteractor {

    override fun getTableTime(idEnterprise: String) {
        profileRepository.getTableTime(idEnterprise)
    }

    override fun getCourts(idEnterprise: String) {
        profileRepository.getCourts(idEnterprise)
    }

    override fun getServices(idEnterprise: String) {
        profileRepository.getServices(idEnterprise)
    }

    override fun getContacts(idEnterprise: String) {
        profileRepository.getContacts(idEnterprise)
    }


    override fun getBasicProfile(idEnterprise : String) {
        profileRepository.getBasicProfile(idEnterprise)
    }

}