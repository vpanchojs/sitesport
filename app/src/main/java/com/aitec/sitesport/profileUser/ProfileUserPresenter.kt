package com.aitec.sitesport.profileUser

interface ProfileUserPresenter {


    fun onSubscribe()

    fun onUnSubscribe()

    fun getInfoUser()

    fun updateInfoUser(names: String, lastName: String, dni: String, phone: String, idUser: String)

}
