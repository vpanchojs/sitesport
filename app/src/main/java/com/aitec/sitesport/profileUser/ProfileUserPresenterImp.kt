package com.aitec.sitesport.profileUser

import android.view.View
import com.aitec.sitesport.entities.User
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
        view.showViewInfo(View.VISIBLE)
        view.showProgressAndMessagin(View.VISIBLE)
        view.showButtonReload(View.GONE)
        interactor.getInfoUser()
    }

    override fun updateInfoUser(names: String, lastnames: String, dni: String, phone: String, photo: String) {
        view.showProgresBar(View.VISIBLE)
        view.showButtonUpdate(View.GONE)
        interactor.updateInfoUser(names, lastnames, dni, phone, photo)
    }

    override fun updatePhotoUser(photo: String) {
        interactor.updatePhotoUser(photo)
        view.showMessagge("Actualizando foto de perfil")
    }

    @Subscribe
    fun onMainEvent(event: ProfileUserEvents) {
        when (event.type) {
            ProfileUserEvents.DNI_INVALID -> {
                view.showErrorDniInput(event.any!!)
                view.showProgresBar(View.GONE)
                view.showButtonUpdate(View.VISIBLE)
            }
            ProfileUserEvents.PHONE_INVALID -> {
                view.showErrorPhoneInput(event.any!!)
                view.showProgresBar(View.GONE)
                view.showButtonUpdate(View.VISIBLE)
            }

            ProfileUserEvents.ON_UPDATE_PROFILE_SUCCESS -> {
                view.showProgresBar(View.GONE)
                view.showButtonUpdate(View.VISIBLE)
                view.showMessagge("Usuario actualizado")

            }
            ProfileUserEvents.ON_UPDATE_PROFILE_ERROR -> {
                view.showProgresBar(View.GONE)
                view.showButtonUpdate(View.VISIBLE)
                view.showMessagge(event.any!!)
            }

            ProfileUserEvents.ON_GET_PROFILE_SUCCESS -> {
                view.showViewInfo(View.GONE)
                view.setInfoUser(event.any as User)
            }

            ProfileUserEvents.ON_GET_PROFILE_ERROR -> {
                view.showViewInfo(View.VISIBLE)
                view.showButtonReload(View.VISIBLE)
                view.showProgressAndMessagin(View.GONE)
            }
            ProfileUserEvents.ON_UPDATE_PHOTO_USER_SUCCESS -> {
                view.setPhoto(event.any.toString())
                view.showMessagge("Foto Actualizada")
            }
            ProfileUserEvents.ON_UPDATE_PHOTO_USER_ERROR -> {
                view.showMessagge(event.any!!)
            }
        }
    }
}
