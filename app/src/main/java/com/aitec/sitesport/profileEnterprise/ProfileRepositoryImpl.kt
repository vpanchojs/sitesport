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

    override fun isAuthenticated() {
        postEvent(ProfileEvent.SUCCESS, ProfileActivity.AUTHENTICATION, null, firebaseApi.isAuthenticated())
    }

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
                postEvent(ProfileEvent.SUCCESS, ProfileActivity.SECTION_BASIC, msg, response)
            }

            override fun onError(error: Any?) {
                postEvent(ProfileEvent.ERROR, ProfileActivity.SECTION_BASIC, error.toString(), null)
            }
        })
    }

    override fun getLike(idUser: String, idEnterprise: String) {
        firebaseApi.getLike(idUser, idEnterprise, object : onApiActionListener<Boolean>{
            override fun onSucces(response: Boolean) {
                val msg: String? = null
                //if(!response.isOnline) msg = MSG_ERROR_CONNECTION
                postEvent(ProfileEvent.SUCCESS, ProfileActivity.SECTION_INITIAL_LIKE, msg, response)
            }

            override fun onError(error: Any?) {
                postEvent(ProfileEvent.ERROR, ProfileActivity.SECTION_INITIAL_LIKE, error.toString(), null)
            }

        })
    }

    override fun toggleLike(idUser: String, idEnterprise: String, isQualified: Boolean) {

        if(isQualified) {
            firebaseApi.removeLike(idUser, idEnterprise, object : onApiActionListener<Int> {
                override fun onSucces(response: Int) {
                    val msg: String? = null
                    postEvent(ProfileEvent.SUCCESS, ProfileActivity.SECTION_UPDATE_LIKE, msg, response)
                }

                override fun onError(error: Any?) {
                    postEvent(ProfileEvent.ERROR, ProfileActivity.SECTION_UPDATE_LIKE, error.toString(), null)
                }
            })
        }else{
            firebaseApi.setLike(idUser, idEnterprise, object : onApiActionListener<Int> {
                override fun onSucces(response: Int) {
                    val msg: String? = null
                    postEvent(ProfileEvent.SUCCESS, ProfileActivity.SECTION_UPDATE_LIKE, msg, response)
                }

                override fun onError(error: Any?) {
                    postEvent(ProfileEvent.ERROR, ProfileActivity.SECTION_UPDATE_LIKE, error.toString(), null)
                }
            })
        }
    }

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