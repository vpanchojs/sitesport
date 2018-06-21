package com.aitec.sitesport.menu

import android.net.Uri
import com.aitec.sitesport.menu.events.MenusEvents

/**
 * Created by victor on 27/1/18.
 */
interface MenusPresenter {

    fun onResume()

    fun onPause()

    fun subscribeAuth()

    fun unSubscribeAuth()

    fun onSingOut()

    fun getMyProfile()

    fun onUpdatePassword(password: String, passwordOld: String)

    fun onEventMenuThread(event: MenusEvents)

    fun tokenFacebook(token: String, name: String?, lastname: String?, email: String?, photoUrl: Uri?)

    fun tokenGoogle(idToken: String, name: String?, lastname: String?, email: String?, photoUrl: Uri?)


}