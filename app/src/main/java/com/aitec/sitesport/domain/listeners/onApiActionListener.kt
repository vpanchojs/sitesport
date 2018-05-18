package com.aitec.sitesport.domain.listeners

/**
 * Created by victor on 15/1/18.
 */
interface onApiActionListener<T> {
    fun onSucces(response: T)
    fun onError(error: Any?)
}