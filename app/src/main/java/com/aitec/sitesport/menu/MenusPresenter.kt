package com.aitec.sitesport.menu

import com.aitec.sitesport.menu.events.MenusEvents

/**
 * Created by victor on 27/1/18.
 */
interface MenusPresenter {

    fun onResume()

    fun onPause()

    fun onSingOut()

    fun getMyProfile()

    fun onUpdatePassword(password: String, passwordOld: String)

    fun onEventMenuThread(event: MenusEvents)

    fun tokenfacebook(token: String?)

    fun tokengoogle(idToken: String?)



}