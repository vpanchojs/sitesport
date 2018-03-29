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
        profileView.showProgressContent()
        profileView.showContentLoading()
        profileInteractor.getProfile(enterprise)
    }

    @Subscribe
    override fun onEventProfileThread(profileEvent: ProfileEvent) {

        when (profileEvent.eventType) {
            ProfileEvent.SUCCESS_PROFILE ->{
                profileView.setEnterprise(profileEvent.eventEnterprise!!)
                profileView.setNameProfile((profileEvent.eventEnterprise as Enterprise).nombres)
                profileView.setImageProfile((profileEvent.eventEnterprise as Enterprise).fotos)
                profileView.setStateEnterprise(if((profileEvent.eventEnterprise as Enterprise).abierto) "Abierto" else "Cerrado")
                profileView.setPriceDayStandar("$ " + (profileEvent.eventEnterprise as Enterprise).precio!![0].dia)
                profileView.setPriceNightStandar("$ " + (profileEvent.eventEnterprise as Enterprise).precio!![0].noche)
                profileView.hideProgressContent()
                profileView.hideContentLoading()

            }

            ProfileEvent.ERROR_PROFILE -> {
                profileView.hideProgressContent()
                profileView.setTextInfoLoading(profileEvent.eventMsg)
                profileView.showTextInfoLoading()
            }
            else -> {
                Log.e("ProfileEvent", "new constant undefine")
            }
        }

    }

}