package com.aitec.sitesport.publication.ui

import com.aitec.sitesport.entities.Publication

interface PublicationView {

    fun showLoading()
    fun hideLoading(msg: Any?)
    fun setPublication(p: Publication)

}