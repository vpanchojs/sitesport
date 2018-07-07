package com.aitec.sitesport.home.ui

import com.aitec.sitesport.entities.Publication

/**
 * Created by Jhony on 28 may 2018.
 */
interface HomeView {

    fun showMessage(message: Any)
    fun showProgressBar(show: Boolean)
    fun addPublication(publication: Publication)
    fun updatePublication(publication: Publication)
    fun removePublication(publication: Publication)
    fun showInfo(msg: String?)
    fun clearListPublications()

}