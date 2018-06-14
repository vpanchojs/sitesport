package com.aitec.sitesport.home.ui

import com.aitec.sitesport.entities.Publications

/**
 * Created by Jhony on 28 may 2018.
 */
interface HomeView {



    fun showMessagge(message: Any)
    fun setResultsSearchs(listNotices: List<Publications>)
    fun showProgresBar(show: Boolean)
    fun clearSearchResults()
    fun clearListNotices()
    fun setDataPublications(publications: List<Publications>)
    fun addPublicacion (publicacion : Publications)
    fun updatePublicacion(publications: Publications)
    fun removePublicacion(publications: Publications)


}