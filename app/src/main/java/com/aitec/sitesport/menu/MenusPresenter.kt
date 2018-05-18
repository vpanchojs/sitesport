package com.aitec.sitesport.menu

import com.aitec.sitesport.menu.events.MenusEvents

/**
 * Created by victor on 27/1/18.
 */
interface MenusPresenter {

    fun onResume()

    fun onPause()

    fun inSession()

    fun onSingOut()

    fun getMyProfile()

    fun onUpdatePassword(password: String, passwordOld: String)

    fun onEventMenuThread(event: MenusEvents)

    fun tokenFacebook(token: String)

    fun tokenGoogle(idToken: String)


}