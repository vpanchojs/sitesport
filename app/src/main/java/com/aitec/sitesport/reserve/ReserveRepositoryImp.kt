package com.aitec.sitesport.reserve

import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.reserve.events.ReserveEvents

class ReserveRepositoryImp(var eventBus: EventBusInterface, sharePreferencesApi: SharePreferencesApi) : ReserveRepository {

    override fun getItemsReserved() {
        postEvent(ReserveEvents.ON_GET_ITEMS_RESERVED_SUCCESS, Any())
    }

    override fun createReserve() {

    }


    private fun postEvent(type: Int, any: Any) {
        var event = ReserveEvents(type, any)
        eventBus.post(event)
    }

}

