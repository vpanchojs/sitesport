package com.aitec.sitesport.profileEnterprise

import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.profileEnterprise.event.ProfileEvent
import com.aitec.sitesport.profileEnterprise.ui.ProfileActivity
import com.aitec.sitesport.profileEnterprise.ui.ProfileView
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Yavac on 16/3/2018.
 */
class ProfilePresenterImpl(var profileView : ProfileView,
                           val profileInteractor : ProfileInteractor,
                           val eventBusInterface : EventBusInterface) : ProfilePresenter{

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

    @Subscribe
    override fun onEventProfileThread(profileEvent: ProfileEvent) {

        if(!(profileEvent.eventObject as Enterprise).isOnline){
            profileView.showMsgInfo(profileEvent.eventMsg!!)
        }

        when(profileEvent.sectionView){

            ProfileActivity.SECTION_BASIC -> {
                val response = (profileEvent.eventObject as Enterprise)
                val e = profileView.getEnterprise()
                e.nombres = response.nombres
                e.descripcion = response.descripcion
                e.foto_perfil = response.foto_perfil
                e.telefono = response.telefono
                e.likes = response.likes
                e.direccion = response.direccion
                profileView.setEnterprise(e)
                profileView.updateBasicProfile()
            }

            ProfileActivity.SECTION_TABLE_TIME -> {
                val e = profileView.getEnterprise()
                e.horario = (profileEvent.eventObject as Enterprise).horario
                profileView.setEnterprise(e)
                profileView.hideLoadingTableTimeSection(profileEvent.eventMsg)
            }

            ProfileActivity.SECTION_COURTS -> {
                profileView.updateCourts((profileEvent.eventObject as Enterprise).canchas)
                profileView.updateImages((profileEvent.eventObject as Enterprise).canchas[0].fotos!!)
                profileView.hideLoadingCourtSection(profileEvent.eventMsg)
            }

            ProfileActivity.SECTION_SERVICES -> {
                profileView.updateServices((profileEvent.eventObject as Enterprise).servicios)
                profileView.hideLoadingServicesSection(profileEvent.eventMsg)
            }

            ProfileActivity.SECTION_CONTACTS -> {
                profileView.updateContacts((profileEvent.eventObject as Enterprise).redesSociales)
                profileView.hideLoadingContactsSection(profileEvent.eventMsg)
            }
        }

    }

}