package com.aitec.sitesport.profileUser

interface ProfileUserInteractor {

    fun getInfoUser()

    fun updateInfoUser(names: String, dni: String, phone: String, photo: String)

}
