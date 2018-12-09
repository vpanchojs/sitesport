package com.aitec.sitesport.reserve.events

class ReserveEvents(var type: Int, var any: Any) {

    companion object {
        val ON_GET_ITEMS_RESERVED_SUCCESS = 0
        val ON_GET_ITEMS_RESERVED_ERROR = 1
        val ON_RESERVED_SUCCESS = 2
        val ON_RESERVED_ERROR = 3
    }

}