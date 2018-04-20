package com.aitec.sitesport.profile

import android.graphics.Bitmap
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

    override fun getProfile(urlDetail : String){
        profileInteractor.getProfile(urlDetail)
    }

    @Subscribe
    override fun onEventProfileThread(profileEvent: ProfileEvent) {

        when (profileEvent.eventType) {
            ProfileEvent.SUCCESS_PROFILE ->{
                //profileView.setNameProfile(profileEvent.eventEnterprise!!.nombres)
                val enterprise: Enterprise = profileEvent.eventObject as Enterprise
                profileView.setImages(enterprise.fotos!!)
                profileView.setLikes(enterprise.likes)
                profileView.setStateEnterprise(enterprise.abierto)
                profileView.setPriceDayStandard(enterprise.precio!![0].dia)
                profileView.setPriceNightStandard(enterprise.precio!![0].noche)
                profileView.setEnterprise(enterprise)
            }

            ProfileEvent.ERROR_PROFILE -> {

            }

            else -> {
                Log.e("ProfileEvent", "new constant undefine")
            }
        }
    }

}