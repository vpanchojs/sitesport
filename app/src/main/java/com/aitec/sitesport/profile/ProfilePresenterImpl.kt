package com.aitec.sitesport.profile

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

    override fun getProfile(pk : String){
        profileInteractor.getProfile(pk)
    }

    @Subscribe
    override fun onEventProfileThread(profileEvent: ProfileEvent) {}

}