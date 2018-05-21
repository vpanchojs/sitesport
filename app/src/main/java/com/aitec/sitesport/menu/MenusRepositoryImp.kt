package com.aitec.sitesport.menu

import com.aitec.sitesport.domain.RetrofitApi
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.domain.SqliteRoomApi
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.Cuenta
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.menu.events.MenusEvents

class MenusRepositoryImp(var eventBus: EventBusInterface, var sharePreferencesApi: SharePreferencesApi, var retrofitApi: RetrofitApi, var sqliteRoomApi: SqliteRoomApi) : MenusRepository {

    override fun inSession() {
        if (sharePreferencesApi.getInSession())
            postEvent(MenusEvents.ON_RECOVERY_SESSION_SUCCESS, sharePreferencesApi.getInSessionPlatform())
        else {
            postEvent(MenusEvents.ON_RECOVERY_SESSION_ERROR, Any())
        }
    }

    override fun enviartokengoogle(idToken: String) {
        retrofitApi.iniciargoogle(idToken, object : onApiActionListener<Cuenta> {
            override fun onSucces(response: Cuenta) {
                sharePreferencesApi.sesion(true, Cuenta.GOOGLE)
                sharePreferencesApi.saveTokenAndSession(response.token!!)
                postEvent(MenusEvents.ON__SIGIN_SUCCES_GOOGLE, response.user!!)
                sqliteRoomApi.setUser(response.user!!, object : onApiActionListener<Long> {
                    override fun onSucces(response: Long) {


                    }

                    override fun onError(error: Any?) {

                    }
                })
            }

            override fun onError(error: Any?) {
                postEvent(MenusEvents.ON__SIGIN_ERROR, error!!)
            }
        })
    }

    override fun enviartoken(token: String) {
        retrofitApi.iniciarfacebook(token, object : onApiActionListener<Cuenta> {
            override fun onSucces(response: Cuenta) {
                sharePreferencesApi.sesion(true, Cuenta.FACEBOOK)
                sharePreferencesApi.saveTokenAndSession(response.token!!)
                postEvent(MenusEvents.ON_SIGIN_SUCCESS_FACEBOOK, response.user!!)

            }

            override fun onError(error: Any?) {
                postEvent(MenusEvents.ON__SIGIN_ERROR, error!!)
            }
        })
    }

    override fun onSingOut() {
        sharePreferencesApi.sesion(false, -1)
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

    private fun postEvent(type: Int, any: Any) {
        var event = MenusEvents(type, any)
        eventBus.post(event)
    }


}