package com.aitec.sitesport.menu

import com.google.android.gms.auth.api.credentials.IdToken

/**
 * Created by victor on 27/1/18.
 */
interface MenusInteractor {


    fun onSingOut()
    fun onUpdatePassword(password: String, passwordOld: String)
    fun getMyProfile()

    fun enviartoken(token:String)

    fun enviartokengoogle(idToken: String)
}