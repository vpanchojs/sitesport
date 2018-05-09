package com.aitec.sitesport.profileUser.ui

interface ProfileUserView {

    fun showMessagge(message: Any)
    fun showProgresBar(show: Int)
    fun showButtonUpdate(show: Int)
    fun showErrorDniInput(message: Any)
    fun showErrorPhoneInput(message: Any)
}