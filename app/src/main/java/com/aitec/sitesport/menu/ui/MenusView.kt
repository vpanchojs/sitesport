package com.aitec.sitesport.menu.ui

import com.aitec.sitesport.entities.User

/**
 * Created by victor on 27/1/18.
 */
interface MenusView {

    fun hideProgressDialog();
    fun showProgressDialog(message: Int)
    fun showMessagge(message: Any)
    fun navigationToProfile();
    fun navigationToTermsAndConditions()
    fun navigationToLogin()
    fun setDataProfile(user: User)
    fun mostrarmenu()

}