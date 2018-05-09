package com.aitec.sitesport.profileUser

interface ProfileUserInteractor {

    fun getInfoUser()

    fun updateInfoUser(names: String, lastName: String, dni: String, phone: String, idUser: String)

}
