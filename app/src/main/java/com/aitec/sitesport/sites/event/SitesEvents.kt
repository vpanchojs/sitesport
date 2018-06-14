package com.aitec.sitesport.sites.event

/**
 * Created by victor on 27/1/18.
 */





class SitesEvents(var type: Int, var any: Any) {

    companion object {

        val ON_GET_SITES_SUCCESS = 0
        val ON_GET_SITES_ERROR = 1

    }
}