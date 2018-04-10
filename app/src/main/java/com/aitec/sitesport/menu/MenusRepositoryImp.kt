package com.aitec.sitesport.menu

import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.menu.events.MenusEvents

/**
 * Created by victor on 27/1/18.
 */
class MenusRepositoryImp(var eventBus: EventBusInterface, var sharePreferencesApi: SharePreferencesApi) : MenusRepository {

    override fun onSingOut() {
        postEvent(MenusEvents.ON_SIGNOUT_SUCCESS, "", Any())
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

    private fun postEvent(type: Int, message: String, any: Any) {
        var event = MenusEvents(type, any, message)
        eventBus.post(event)
    }
}