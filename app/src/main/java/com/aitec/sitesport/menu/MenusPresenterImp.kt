package com.aitec.sitesport.menu

import android.util.Log
import com.aitec.sitesport.entities.User
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.menu.events.MenusEvents
import com.aitec.sitesport.menu.ui.MenusView
import org.greenrobot.eventbus.Subscribe

class MenusPresenterImp(var eventBus: EventBusInterface, var view: MenusView, var interactor: MenusInteractor) : MenusPresenter {






    override fun tokengoogle(idToken: String?) {

        interactor.enviartokengoogle(idToken!!)

    }

    override fun tokenfacebook(token: String?) {


        interactor.enviartoken(token!!)


    }

    override fun onResume() {
        eventBus.register(this)

    }

    override fun onPause() {
        eventBus.unregister(this)
    }

    override fun onSingOut() {
        interactor.onSingOut()
    }

    override fun getMyProfile() {
        interactor.getMyProfile()
    }

    override fun onUpdatePassword(password: String, passwordOld: String) {
        //view.showProgressDialog(R.string.update_password)
        interactor.onUpdatePassword(password, passwordOld)
    }

    @Subscribe
    override fun onEventMenuThread(event: MenusEvents) {
        when (event.type) {
            MenusEvents.ON_SIGNOUT_SUCCESS -> {
                view.navigationToLogin()
            }
            MenusEvents.ON_UPDATE_PASSWORD_SUCCESS -> {
                view.hideProgressDialog()
                view.showMessagge(event.message)
            }
            MenusEvents.ON_UPDATE_PASSWORD_ERROR -> {
                view.hideProgressDialog()
                view.showMessagge(event.message)
            }
            MenusEvents.ON_GET_MY_PROFILE_SUCCESS -> {
                Log.e("profile", "llegue")
                view.setDataProfile(event.any as User)
            }
            MenusEvents.ON_GET_MY_PROFILE_ERROR -> {
                view.showMessagge(event.message)
            }

            MenusEvents.ON_SUCCESS_FACEBOOK->{
                view.showMessagge(event.message)
                view.mostrarmenu()
            }
            MenusEvents.ON_ON_ERROR->{
                view.showMessagge(event.message)
            }
            MenusEvents.ON_SUCCES_GOOGLE->{
                view.showMessagge(event.message)
                view.mostrarmenu()
            }
            MenusEvents.ON_ERROR_GOOGLE->{
                view.showMessagge(event.message)
            }

        }
    }
}