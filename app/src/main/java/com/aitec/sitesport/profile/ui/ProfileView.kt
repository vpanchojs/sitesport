package com.aitec.sitesport.profile.ui

import com.aitec.sitesport.entities.enterprise.*

/**
 * Created by Yavac on 12/3/2018.
 */
interface ProfileView {
    fun setEnterprise(enterprise: Enterprise)
    fun setLikes(likes: Int)
    fun setImages(imagesUrls: List<Foto>)
    fun setStateEnterprise(state: Boolean)
    fun setServices(servicios: Servicios)
    fun setTableTime(horarios: List<Horario>)
    fun setCourts(canchas: List<Cancha>)

    fun showLoading()
    fun hideLoading(msg: String)
}