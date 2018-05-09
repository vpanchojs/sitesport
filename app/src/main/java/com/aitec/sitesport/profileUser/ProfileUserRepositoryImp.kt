package com.aitec.sitesport.profileUser

import com.aitec.sitesport.domain.RetrofitApi
import com.aitec.sitesport.entities.User
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.profileUser.events.ProfileUserEvents

class ProfileUserRepositoryImp(var eventBus: EventBusInterface, var retrofitApi: RetrofitApi) : ProfileUserRepository {

    override fun getInfoUser() {

    }

    override fun updateInfoUser(user: User) {

    }

    private fun postEvent(type: Int, any: Any) {
        var event = ProfileUserEvents(type, any)
        eventBus.post(event)
    }


}