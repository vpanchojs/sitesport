package com.aitec.sitesport.profileEnterprise.ui

import com.aitec.sitesport.entities.enterprise.*

/**
 * Created by Yavac on 12/3/2018.
 */
interface ProfileView {

    fun showSnackBarInfo(msg: String)
    fun showToastInfo(msg: String)

    fun showLoadingTableTimeSection()
    fun showLoadingCourtSection()
    fun showLoadingServicesSection()
    fun showLoadingContactsSection()

    fun hideLoadingTableTimeSection(msg: String?)
    fun hideLoadingCourtSection(msg: String?)
    fun hideLoadingServicesSection(msg: String?)
    fun hideLoadingContactsSection(msg: String?)

    fun updateBasicProfile()
    fun updateCourts(courtList: List<Cancha>)
    fun updateImages(imageList: ArrayList<String>)
    fun updateServices(serviceList: List<Servicio>)
    fun updateContacts(contactList: List<RedSocial>)

    fun updateLike(like: Int)
    fun isQualified(qualify: Boolean)
    fun restoreRating()
    fun getEnterprise() : Enterprise
    fun setEnterprise(enterprise: Enterprise)
    fun authenticated(uidUser: Any?)
}