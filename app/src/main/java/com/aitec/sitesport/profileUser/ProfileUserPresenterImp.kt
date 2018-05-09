package com.aitec.sitesport.profileUser

import android.view.View
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.profileUser.events.ProfileUserEvents
import com.aitec.sitesport.profileUser.ui.ProfileUserView
import org.greenrobot.eventbus.Subscribe

class ProfileUserPresenterImp(var eventBus: EventBusInterface, var view: ProfileUserView, var interactor: ProfileUserInteractor) : ProfileUserPresenter {


    override fun onSubscribe() {
        eventBus.register(this)
    }

    override fun onUnSubscribe() {
        eventBus.unregister(this)
    }

    override fun getInfoUser() {
        interactor.getInfoUser()
    }

    override fun updateInfoUser(names: String, lastName: String, dni: String, phone: String, idUser: String) {
        view.showProgresBar(View.VISIBLE)
        view.showButtonUpdate(View.GONE)
        interactor.updateInfoUser(names, lastName, dni, phone, idUser)
    }

    @Subscribe
    fun onMainEvent(event: ProfileUserEvents) {
        when (event.type) {
            ProfileUserEvents.DNI_INVALID -> {
                view.showErrorDniInput(event.any)
                view.showProgresBar(View.GONE)
                view.showButtonUpdate(View.VISIBLE)
            }
            ProfileUserEvents.PHONE_INVALID -> {
                view.showErrorPhoneInput(event.any)
                view.showProgresBar(View.GONE)
                view.showButtonUpdate(View.VISIBLE)
            }
        }
    }
}
