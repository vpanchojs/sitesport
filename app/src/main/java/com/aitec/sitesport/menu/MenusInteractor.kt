package com.aitec.sitesport.menu

import android.net.Uri

/**
 * Created by victor on 27/1/18.
 */
interface MenusInteractor {


    fun subscribeAuth()

    fun unSubscribeAuth()

    fun onSingOut()
    fun onUpdatePassword(password: String, passwordOld: String)
    fun getMyProfile()
    fun enviartoken(token: String, name: String?, lastname: String?, email: String?, photoUrl: Uri?)

    fun enviartokengoogle(idToken: String, name: String?, lastname: String?, email: String?, photoUrl: Uri?)
}