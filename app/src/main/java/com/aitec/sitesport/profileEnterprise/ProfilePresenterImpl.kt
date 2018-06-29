package com.aitec.sitesport.profileEnterprise

import android.util.Log
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.profileEnterprise.event.ProfileEvent
import com.aitec.sitesport.profileEnterprise.ui.ProfileActivity
import com.aitec.sitesport.profileEnterprise.ui.ProfileView
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Yavac on 16/3/2018.
 */
class ProfilePresenterImpl(var profileView: ProfileView,
                           val profileInteractor: ProfileInteractor,
                           val eventBusInterface: EventBusInterface) : ProfilePresenter {

    override fun isAuthenticated() {
        profileInteractor.isAuthenticated()
    }

    override fun register() {
        eventBusInterface.register(this)
    }

    override fun unregister() {
        eventBusInterface.unregister(this)
    }

    override fun getBasicProfile(idEnterprise: String) {
        profileInteractor.getBasicProfile(idEnterprise)
    }

    override fun getTableTimeProfile(idEnterprise: String) {
        profileView.showLoadingTableTimeSection()
        profileInteractor.getTableTime(idEnterprise)
    }

    override fun getCourtsProfile(idEnterprise: String) {
        profileView.showLoadingCourtSection()
        profileInteractor.getCourts(idEnterprise)

    }

    override fun getServicesProfile(idEnterprise: String) {
        profileView.showLoadingServicesSection()
        profileInteractor.getServices(idEnterprise)
    }

    override fun getContactsProfile(idEnterprise: String) {
        profileView.showLoadingContactsSection()
        profileInteractor.getContacts(idEnterprise)
    }

    override fun getLike(idUser: String, idEnterprise: String) {
        profileInteractor.getLike(idUser, idEnterprise)
    }

    override fun toggleLike(idUser: String, idEnterprise: String, isQualified: Boolean) {
        profileInteractor.toggleLike(idUser, idEnterprise, isQualified)
    }

    private fun existConnection(event: ProfileEvent) {
        if (!(event.eventObject as Enterprise).isOnline) {
            Log.e("CONNECTION", "no hay conexion")
            profileView.showSnackBarInfo(event.eventMsg!!)
        }
    }

    @Subscribe
    override fun onEventProfileThread(profileEvent: ProfileEvent) {

        when (profileEvent.sectionView) {

            ProfileActivity.AUTHENTICATION -> {
                profileView.authenticated(profileEvent.eventObject)
            }

            ProfileActivity.SECTION_BASIC -> {
                val response = (profileEvent.eventObject as Enterprise)
                val e = profileView.getEnterprise()
                e.nombre = response.nombre
                e.descripcion = response.descripcion
                e.foto_perfil = response.foto_perfil
                e.telefono = response.telefono
                e.me_gustas = response.me_gustas
                e.direccion = response.direccion
                profileView.setEnterprise(e)
                profileView.updateBasicProfile()
                existConnection(profileEvent)
            }

            ProfileActivity.SECTION_TABLE_TIME -> {
                val e = profileView.getEnterprise()
                e.horarios = (profileEvent.eventObject as Enterprise).horarios
                e.abierto = (profileEvent.eventObject as Enterprise).abierto
                profileView.setEnterprise(e)
                profileView.isOpen(e.abierto)
                profileView.hideLoadingTableTimeSection(profileEvent.eventMsg)
                existConnection(profileEvent)
            }

            ProfileActivity.SECTION_COURTS -> {
                profileView.updateCourts((profileEvent.eventObject as Enterprise).canchas)
                if ((profileEvent.eventObject as Enterprise).canchas.isNotEmpty())
                    profileView.updateImages((profileEvent.eventObject as Enterprise).canchas[0].fotos!!)
                profileView.hideLoadingCourtSection(profileEvent.eventMsg)
                existConnection(profileEvent)
            }

            ProfileActivity.SECTION_SERVICES -> {
                profileView.updateServices((profileEvent.eventObject as Enterprise).servicios)
                profileView.hideLoadingServicesSection(profileEvent.eventMsg)
                existConnection(profileEvent)
            }

            ProfileActivity.SECTION_CONTACTS -> {
                profileView.updateContacts((profileEvent.eventObject as Enterprise).redes_social)
                profileView.hideLoadingContactsSection(profileEvent.eventMsg)
                existConnection(profileEvent)
            }

            ProfileActivity.SECTION_INITIAL_LIKE -> {
                profileView.isQualified((profileEvent.eventObject as Boolean))
            }

            ProfileActivity.SECTION_UPDATE_LIKE -> {
                if (profileEvent.eventType == ProfileEvent.SUCCESS)
                    profileView.updateLike((profileEvent.eventObject as Int))
                else {
                    profileView.showToastInfo(profileEvent.eventMsg!!)
                    profileView.restoreRating()
                }
            }
        }

    }

}