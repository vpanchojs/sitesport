package com.aitec.sitesport.publication.ui

import com.aitec.sitesport.entities.Publication

interface PublicationView {

    fun showPbLoading()
    fun hidePbLoading()
    fun showSnackBar(msg: String)
    fun setPublication(p: Publication)

}