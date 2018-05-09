package com.aitec.sitesport.profileUser.events

class ProfileUserEvents(var type: Int, var any: Any) {

    companion object {
        val DNI_INVALID = 0
        val PHONE_INVALID = 1
        val ON_GET_PROFILE_SUCCESS = 2
        val ON_GET_PROFILE_ERROR = 3
        val ON_UPDATE_PROFILE_SUCCESS = 4
        val ON_UPDATE_PROFILE_ERROR = 5
    }
}