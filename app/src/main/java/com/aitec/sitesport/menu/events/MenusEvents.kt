package com.aitec.sitesport.menu.events

/**
 * Created by victor on 27/1/18.
 */
class MenusEvents(var type: Int, var any: Any) {

    companion object {
        val ON_GET_MY_PROFILE_SUCCESS = 4
        val ON_GET_MY_PROFILE_ERROR = 5
        val ON_SIGNOUT_SUCCESS = 0
        val ON_SIGNOUT_ERROR = 1
        val ON_UPDATE_PASSWORD_SUCCESS = 2
        val ON_UPDATE_PASSWORD_ERROR = 3


        val ON_SIGIN_SUCCESS_FACEBOOK = 7
        val ON__SIGIN_ERROR = 6
        val ON__SIGIN_SUCCES_GOOGLE = 9
        val ON_RECOVERY_SESSION_SUCCESS = 10
        val ON_RECOVERY_SESSION_ERROR = 11


    }
}