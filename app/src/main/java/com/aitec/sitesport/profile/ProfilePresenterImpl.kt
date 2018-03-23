package com.aitec.sitesport.profile

import android.util.Log
import com.aitec.sitesport.entities.enterprise.Enterprise
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


    override fun onResume() {
        eventBusInterface.register(this)
    }

    override fun onPause() {
        eventBusInterface.unregister(this)
    }

    override fun onDestroy() {}

    override fun getProfile(enterprise : Enterprise?){
        profileInteractor.getProfile(enterprise)
    }

    @Subscribe
    override fun onEventProfileThread(profileEvent: ProfileEvent) {

        when (profileEvent.eventType) {
            ProfileEvent.SUCCESS_PROFILE ->{

                //{"nombres":"Calva y Calva","
                // descripcion":"Calva y Calva",
                // "fotos":[{"imagen":"http://localhost:8050/media/imagenes/acaf0348-593b-4374-9f08-9144f446aece/numpy.jpg "}],
                // "telefonos":[],
                // "categoria":[{"descripcion":"asda","nombre":"FUTBOOL"}]
                // ,"red_social":[],
                // "precio":[],
                // "horario":[{"nombre":"Nocturno"}],"hora":[]}

                Log.e("onEventProfileThread", "SUCCESS_PROFILE")
                profileView.setNameProfile((profileEvent.eventEnterprise as Enterprise).nombres)
                profileView.setImageProfile((profileEvent.eventEnterprise as Enterprise).fotos!!)
                profileView.setPriceHourDay((profileEvent.eventEnterprise as Enterprise).precio!!)
                profileView.setPriceHourNight((profileEvent.eventEnterprise as Enterprise).precio!!)
                profileView.setEnterprise(profileEvent.eventEnterprise!!)
            }

            ProfileEvent.ERROR_PROFILE -> {

            }
            else -> {
                Log.e("ProfileEvent", "new constant undefine")
            }
        }

        //"categoria":[{"nombre":"FUTBOOL","descripcion":"asda"}],"red_social":[],"horario":[{"nombre":"Nocturno"}],"hora":[]}

    }

}