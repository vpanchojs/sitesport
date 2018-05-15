package com.aitec.sitesport.reserve.events

class ReserveEvents(var type: Int, var any: Any) {

    companion object {
        val ON_GET_ITEMS_RESERVED_SUCCESS = 0
        val ON_GET_ITEMS_RESERVED_ERROR = 0
    }

}