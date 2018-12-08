package com.aitec.sitesport.menu

import android.util.Log
import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.Cuenta
import com.aitec.sitesport.entities.User
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.menu.events.MenusEvents

class MenusRepositoryImp(var eventBus: EventBusInterface, var sharePreferencesApi: SharePreferencesApi, var firebaseApi: FirebaseApi) : MenusRepository {

    override fun subscribeAuth() {
        firebaseApi.suscribeAuth(object : onApiActionListener<User> {
            override fun onSucces(response: User) {
                postEvent(MenusEvents.ON_RECOVERY_SESSION_SUCCESS, response)
            }

            override fun onError(error: Any?) {
                postEvent(MenusEvents.ON_RECOVERY_SESSION_ERROR, Any())
            }
        })
    }

    override fun unSubscribeAuth() {
        firebaseApi.unSuscribeAuth()
    }

    override fun enviartokengoogle(idToken: String, user: User) {
        firebaseApi.autenticationGoogle(idToken, user, object : onApiActionListener<User> {
            override fun onSucces(response: User) {
                sharePreferencesApi.sesion(true, Cuenta.GOOGLE)
                postEvent(MenusEvents.ON__SIGIN_SUCCES_GOOGLE, response)
            }

            override fun onError(error: Any?) {
                postEvent(MenusEvents.ON__SIGIN_ERROR, error)
            }
        })

    }

    override fun enviartoken(token: String, user: User) {
        firebaseApi.autenticationFacebook(token, user, object : onApiActionListener<User> {
            override fun onSucces(response: User) {
                sharePreferencesApi.sesion(true, Cuenta.FACEBOOK)
                postEvent(MenusEvents.ON_SIGIN_SUCCESS_FACEBOOK, response)
            }

            override fun onError(error: Any?) {
                postEvent(MenusEvents.ON__SIGIN_ERROR, error)
            }

        })

    }

    override fun onSingOut() {
        firebaseApi.sigOut()
        postEvent(MenusEvents.ON_SIGNOUT_SUCCESS, sharePreferencesApi.getInSessionPlatform())
    }

    override fun onUpdatePassword(password: String, passwordOld: String) {

    }

    override fun getMyProfile() {
/*
        objectBoxApi.getuserInSession(object : onBoxActionListener {
            override fun onSucces(any: Any?) {
                postEvent(MenusEvents.ON_GET_MY_PROFILE_SUCCESS, "", any!!)
            }

            override fun onError(any: Any?) {
                postEvent(MenusEvents.ON_GET_MY_PROFILE_ERROR, any.toString(), Any())
            }
        })
    */
    }

    private fun postEvent(type: Int, any: Any?) {
        var event = MenusEvents(type, any)
        eventBus.post(event)
    }


}