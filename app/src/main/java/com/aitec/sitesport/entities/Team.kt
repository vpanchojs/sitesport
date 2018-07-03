package com.aitec.sitesport.entities

class Team {
    var pk = ""
    var foto: String = ""
    var descripcion: String = ""
    var nombre: String = "equipo"
    var deportes: List<Sport>? = null
    lateinit var jugadores: List<String>
    var grupo: String = ""
    var marcador: Int = 0
}