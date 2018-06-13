package com.aitec.sitesport.profileEnterprise

import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.profileEnterprise.event.ProfileEvent
import com.aitec.sitesport.profileEnterprise.ui.ProfileActivity


/**
 * Created by Yavac on 16/3/2018.
 */
class ProfileRepositoryImpl(var firebaseApi: FirebaseApi,
                            var eventBusInterface : EventBusInterface,
                            var sharePreferencesApi: SharePreferencesApi) : ProfileRepository{

    override fun getTableTime(idEnterprise: String) {
        firebaseApi.getTableTimeProfile(idEnterprise, object : onApiActionListener<Enterprise>{
            override fun onSucces(response: Enterprise) {
                var msg: String? = null
                if(!response.isOnline) msg = MSG_ERROR_CONNECTION
                postEvent(ProfileEvent.SUCCESS, ProfileActivity.SECTION_TABLE_TIME, msg, response)
            }

            override fun onError(error: Any?) {
                postEvent(ProfileEvent.ERROR, ProfileActivity.SECTION_TABLE_TIME, error.toString(), null)
            }

        })
    }

    override fun getCourts(idEnterprise: String) {
        firebaseApi.getCourts(idEnterprise, object : onApiActionListener<Enterprise>{
            override fun onSucces(response: Enterprise) {
                var msg: String? = null
                if(!response.isOnline) msg = MSG_ERROR_CONNECTION
                postEvent(ProfileEvent.SUCCESS, ProfileActivity.SECTION_COURTS, msg, response)
            }

            override fun onError(error: Any?) {
                postEvent(ProfileEvent.ERROR, ProfileActivity.SECTION_COURTS, error.toString(), null)
            }

        })
    }

    override fun getServices(idEnterprise: String) {
        firebaseApi.getServices(idEnterprise, object : onApiActionListener<Enterprise>{
            override fun onSucces(response: Enterprise) {
                var msg: String? = null
                if(!response.isOnline) msg = MSG_ERROR_CONNECTION
                postEvent(ProfileEvent.SUCCESS, ProfileActivity.SECTION_SERVICES, msg, response)
            }

            override fun onError(error: Any?) {
                postEvent(ProfileEvent.ERROR, ProfileActivity.SECTION_SERVICES, error.toString(), null)
            }

        })
    }

    override fun getContacts(idEnterprise: String) {
        firebaseApi.getContacts(idEnterprise, object : onApiActionListener<Enterprise>{
            override fun onSucces(response: Enterprise) {
                var msg: String? = null
                if(!response.isOnline) msg = MSG_ERROR_CONNECTION
                postEvent(ProfileEvent.SUCCESS, ProfileActivity.SECTION_CONTACTS, msg, response)
            }

            override fun onError(error: Any?) {
                postEvent(ProfileEvent.ERROR, ProfileActivity.SECTION_CONTACTS, error.toString(), null)
            }

        })
    }


    override fun stopRequests() {
    }

    override fun getBasicProfile(idEnterprise : String) {

        firebaseApi.getBasicProfile(idEnterprise, object : onApiActionListener<Enterprise> {
            override fun onSucces(response: Enterprise) {
                var msg: String? = null
                if(!response.isOnline) msg = MSG_ERROR_CONNECTION
                postEvent(ProfileEvent.SUCCESS, ProfileActivity.SECTION_BASIC, msg, response)            }

            override fun onError(error: Any?) {
                postEvent(ProfileEvent.ERROR, ProfileActivity.SECTION_BASIC, error.toString(), null)
            }

        })
    }
        /*retrofitApi.getBasicProfile(urlDetail, object : onApiActionListener<Enterprise> {
            override fun onSucces(response: Enterprise) {
                Log.e("ProfileRepositoryImpl", response.toString())

                /*val gsonBuilder = GsonBuilder()
                gsonBuilder.serializeNulls()
                val gson = gsonBuilder.create()

                postEvent(ProfileEvent.SUCCESS_PROFILE, "",
                        gson.fromJson(response.toString(), Enterprise::class.java))*/
                postEvent(ProfileEvent.SUCCESS_PROFILE, "", response)
            }

            override fun onError(error: Any?) {
                Log.e("ProfileRepositoryImpl", error.toString())
                postEvent(ProfileEvent.ERROR_PROFILE, error.toString(), null)
            }
        })*/

    /*}*/

    private fun saveIdEnterprise(pk : String){
        sharePreferencesApi.savePkEnterprise(pk)
    }

    private fun postEvent(type: Int, section: Int, any: Any?, eventObject: Any?) {
        val event = ProfileEvent(type, section, any.toString(), eventObject)
        eventBusInterface.post(event)
    }

    companion object {
        const val MSG_ERROR_CONNECTION = "Sin conexión a internet."
    }

}