package com.aitec.sitesport.profileUser.events

class ProfileUserEvents(var type: Int, var any: Any) {

    companion object {
        val DNI_INVALID = 0
        val PHONE_INVALID = 1


    }
}