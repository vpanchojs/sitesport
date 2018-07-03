package com.aitec.sitesport.entities

class ItemCalendar() {
    var pk: String = ""
    var fecha: String = ""
    var hora: Int = 0
    var genero: String = ""
    var estado: Int = 0
    lateinit var equipo1: Team
    lateinit var equipo2: Team
    var deporte = ""

    companion object {
        const val SIN_JUGAR = 0
        const val JUGANDO = 1
        const val FINALIZO = 2

    }
}