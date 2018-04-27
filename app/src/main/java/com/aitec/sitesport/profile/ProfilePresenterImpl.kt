package com.aitec.sitesport.profile

import android.graphics.Bitmap
import android.util.Log
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.entities.enterprise.Foto
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.profile.event.ProfileEvent
import com.aitec.sitesport.profile.ui.ProfileView
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

    override fun onDestroy() {}

    override fun getProfile(urlDetail : String){
        profileView.showLoading()
        profileInteractor.getProfile(urlDetail)
    }

    @Subscribe
    override fun onEventProfileThread(profileEvent: ProfileEvent) {

        when (profileEvent.eventType) {
            ProfileEvent.SUCCESS_PROFILE ->{
                //profileView.setNameProfile(profileEvent.eventEnterprise!!.nombres)
                Log.e("ProfilePresenterImpl", "OnSuccesProfile")
                val enterprise: Enterprise = profileEvent.eventObject as Enterprise
                val fotoEnterprise: MutableList<Foto> = mutableListOf()
                val f= Foto()
                f.imagen = enterprise.foto_perfil
                fotoEnterprise.add(f) // solo para la imagen principal de la empresa de perfil
                profileView.setImages(fotoEnterprise)
                profileView.setLikes(enterprise.puntuacion)
                profileView.setTableTime(enterprise.horario)
                profileView.setStateEnterprise(enterprise.abierto)
                profileView.setCourts(enterprise.canchas)
                profileView.setServices(enterprise.servicios)
                profileView.setEnterprise(enterprise)
                profileView.hideLoading(profileEvent.eventMsg!!)
            }

            ProfileEvent.ERROR_PROFILE -> {
                profileView.hideLoading(profileEvent.eventMsg!!)
            }

            else -> {
                Log.e("ProfileEvent", "new constant undefine")
            }
        }
    }

}