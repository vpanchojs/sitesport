package com.aitec.sitesport.sites

import com.aitec.sitesport.util.Filtros

class SitesInteractorImp(var repository: SitesRepository) : SitesInteractor {

    companion object {
        const val FILTER_SCORE: String = "puntuacion"
        const val FILTER_OPEN: String = "abierto"
        const val FILTER_LOCATION: String = "ubicacion"

    }

    //val parametros = HashMap<String, String>()

    override fun onGetSites() {
        repository.onGetSites()
    }

    override fun addFilterOpen(add: Boolean) {
        val parametros = HashMap<String, String>()
        /*
        if (add) {
            parametros.put(FILTER_OPEN, "2")
        } else {
            parametros.remove(FILTER_OPEN)
        }
        */
        repository.onGetSites(parametros)
    }

    override fun addFilterScore(add: Boolean) {
        val parametros = HashMap<String, String>()
        parametros.put(FILTER_SCORE, "-puntuacion")
        /*
        if (add) {
            parametros.put(FILTER_SCORE, "-puntuacion")
        } else {
            parametros.remove(FILTER_SCORE)
        }
        */
        repository.onGetSites(parametros)
    }


    /*
    override fun addFilterLocation(ubicacion: String, add: Boolean) {
        var filtros=Filtros()
        filtros.cercanos=true
        filtros.latitude=

        val parametros = Filtros().toMap()

        //parametros.put(FILTER_LOCATION, ubicacion)

        /*
        if (add) {
            parametros.put(FILTER_LOCATION, ubicacion)
        } else {
            parametros.remove(FILTER_LOCATION)
        }
        */
        repository.onGetSites(parametros)
    }
    */

    override fun addFilterLocation(latitude: Double, longitude: Double, add: Boolean) {
        var filtros = Filtros()
        filtros.cercanos = true
        filtros.latitude = latitude
        filtros.longitude = longitude

        //parametros.put(FILTER_LOCATION, ubicacion)

        /*
        if (add) {
            parametros.put(FILTER_LOCATION, ubicacion)
        } else {
            parametros.remove(FILTER_LOCATION)
        }
        */
        repository.onGetSitesLocation(filtros.toMap())
    }
}