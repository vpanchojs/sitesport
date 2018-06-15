package com.aitec.sitesport.profileUser

interface ProfileUserInteractor {

    fun getInfoUser()

    fun updateInfoUser(names: String,lastnames:String, dni: String, phone: String, photo: String)

    fun updatePhotoUser(photo: String)

}
