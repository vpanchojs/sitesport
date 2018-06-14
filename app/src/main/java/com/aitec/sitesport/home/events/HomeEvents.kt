package com.aitec.sitesport.home.events

/**
 * Created by Jhony on 28 may 2018.
 */





class HomeEvents (var type: Int, var any: Any?) {

    companion object {
        val ON_ADD_PUBLISH = 0
        val ON_UPDATE_PUBLISH = 1
        val ON_REMOVE_PUBLISH= 2
        val ON_ERROR_PUBLISH = 3

    }

}