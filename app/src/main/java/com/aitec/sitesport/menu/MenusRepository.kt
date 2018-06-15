package com.aitec.sitesport.menu

import com.aitec.sitesport.entities.User

/**
 * Created by victor on 27/1/18.
 */
interface MenusRepository {
    fun onSingOut()
    fun onUpdatePassword(password: String, passwordOld: String)
    fun getMyProfile()
    fun enviartoken(token: String, user: User)
    fun enviartokengoogle(idToken: String, user: User)
    fun subscribeAuth()
    fun unSubscribeAuth()
}