package com.aitec.sitesport.profileUser

interface ProfileUserPresenter {


    fun onSubscribe()

    fun onUnSubscribe()

    fun getInfoUser()

    fun updateInfoUser(names: String, lastname: String, dni: String, phone: String, photo: String)

    fun updatePhotoUser(photo: String)

}
