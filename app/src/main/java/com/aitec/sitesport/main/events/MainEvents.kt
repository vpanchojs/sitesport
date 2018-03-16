package com.aitec.sitesport.main.events

/**
 * Created by victor on 27/1/18.
 */
class MainEvents(var type: Int, var any: Any) {

    companion object {
        val ON_RESULTS_SEARCHS_SUCCESS = 0
        val ON_RESULTS_SEARCHS_ERROR = 1

    }
}