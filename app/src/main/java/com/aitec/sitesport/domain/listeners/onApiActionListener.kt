package com.aitec.sitesport.domain.listeners

/**
 * Created by victor on 15/1/18.
 */
interface onApiActionListener {
    fun onSucces(response: Any?)
    fun onError(error: Any?)
}