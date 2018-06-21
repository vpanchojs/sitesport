package com.aitec.sitesport.domain.listeners

/**
 * Created by Jhony on 13 jun 2018.
 */
interface RealTimeListener<T> {
    fun addDocument(response: T)
    fun removeDocument(response: T)
    fun updateDocument(response: T)
    fun omError(error: Any)


}