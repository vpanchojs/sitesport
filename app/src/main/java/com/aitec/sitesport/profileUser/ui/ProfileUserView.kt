package com.aitec.sitesport.profileUser.ui

interface ProfileUserView {

    fun showMessagge(message: Any)
    fun showProgresBar(show: Int)
    fun showButtonUpdate(show: Int)
    fun showErrorDniInput(message: Any)
    fun showErrorPhoneInput(message: Any)
    fun showViewInfo(visibility: Int)
    fun setInfoUser(dni: String?, phone: String?)
    fun showButtonReload(visibility: Int)
    fun showProgressAndMessagin(visibility: Int)
}