package com.aitec.sitesport.mapSites.events

/**
 * Created by victor on 27/1/18.
 */
class MapSitesEvents(var type: Int, var any: Any) {

    companion object {
        val ON_RESULTS_SEARCHS_SUCCESS = 0
        val ON_RESULTS_SEARCHS_ERROR = 1
        val ON_RESULTS_SEARCH_NAMES_SUCCESS = 2
        val ON_RESULTS_SEARCH_NAMES_ERROR = 3

    }
}