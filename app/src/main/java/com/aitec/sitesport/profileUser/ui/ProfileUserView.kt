package com.aitec.sitesport.profileUser.ui

import com.aitec.sitesport.entities.User

interface ProfileUserView {

    fun showMessagge(message: Any)
    fun showProgresBar(show: Int)
    fun showButtonUpdate(show: Int)
    fun showErrorDniInput(message: Any)
    fun showErrorPhoneInput(message: Any)
    fun showViewInfo(visibility: Int)
    fun setInfoUser(user: User)
    fun showButtonReload(visibility: Int)
    fun showProgressAndMessagin(visibility: Int)
    fun setPhoto(url: String)
}