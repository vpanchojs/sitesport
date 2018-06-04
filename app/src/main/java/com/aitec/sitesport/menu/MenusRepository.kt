package com.aitec.sitesport.menu

/**
 * Created by victor on 27/1/18.
 */
interface MenusRepository {
    fun onSingOut()
    fun onUpdatePassword(password: String, passwordOld: String)
    fun getMyProfile()
    fun enviartoken(token: String)
    fun enviartokengoogle(idToken: String)
    fun subscribeAuth()
    fun unSubscribeAuth()
}