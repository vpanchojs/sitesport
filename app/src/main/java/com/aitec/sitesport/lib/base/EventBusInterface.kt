package com.aitec.sitesport.lib.base

interface EventBusInterface {

    fun register(subscriber: Any?)
    fun unregister(subscriber: Any?)
    fun post(subscriber: Any?)

}