package com.aitec.sitesport.util

import com.google.firebase.firestore.Exclude
import java.util.HashMap

class Filtros {
    var puntuaciones = false
    var abiertos=true
    var cercanos=false
    var latitude=0.0
    var longitude=0.0


    @Exclude
    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["puntuaciones"] = puntuaciones
        result["abiertos"] = abiertos
        result["cercanos"] = cercanos
        result["latitude"] = latitude
        result["longitude"] = longitude
        return result
    }

}