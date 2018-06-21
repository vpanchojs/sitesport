package com.aitec.sitesport.menu

import android.net.Uri
import android.view.View
import com.aitec.sitesport.entities.User
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.menu.events.MenusEvents
import com.aitec.sitesport.menu.ui.MenusView
import org.greenrobot.eventbus.Subscribe

class MenusPresenterImp(var eventBus: EventBusInterface, var view: MenusView, var interactor: MenusInteractor) : MenusPresenter {

    override fun tokenGoogle(idToken: String, name: String?, lastname: String?, email: String?, photoUrl: Uri?) {
        view.showProgress(View.VISIBLE)
        view.visibleLogin(View.INVISIBLE)
        interactor.enviartokengoogle(idToken, name, lastname, email, photoUrl)
    }

    override fun tokenFacebook(token: String, name: String?, lastname: String?, email: String?, photoUrl: Uri?) {
        view.showProgress(View.VISIBLE)
        view.visibleLogin(View.INVISIBLE)
        interactor.enviartoken(token, name, lastname, email, photoUrl)
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


    override fun subscribeAuth() {
        //view.showProgress(View.VISIBLE)
        //view.visibleLogin(View.INVISIBLE)
        interactor.subscribeAuth()
    }

    override fun unSubscribeAuth() {
        interactor.unSubscribeAuth()
    }

    override fun getMyProfile() {
        interactor.getMyProfile()
    }

    override fun onUpdatePassword(password: String, passwordOld: String) {
        //view.showLoadingTableTimeSection(R.string.update_password)
        interactor.onUpdatePassword(password, passwordOld)
    }

    @Subscribe
    override fun onEventMenuThread(event: MenusEvents) {
        when (event.type) {
            MenusEvents.ON_SIGNOUT_SUCCESS -> {
                view.visibleMenuOptions(View.GONE)
                view.visibleLogin(View.VISIBLE)
                view.singOut(event.any as Int)

                /*
                *
                * */
                var siteportUser = User()
                siteportUser.nombre = "SiteSport"
                siteportUser.correo_electronico = "soporte@aitecec.com"
                view.setDataProfile(siteportUser)

            }
            MenusEvents.ON_SIGIN_SUCCESS_FACEBOOK -> {
                view.showProgress(View.GONE)
                view.setDataProfile(event.any as User)
                view.visibleMenuOptions(View.VISIBLE)
                view.visibleLogin(View.GONE)
                view.setDataProfile(event.any as User)

            }
            MenusEvents.ON__SIGIN_SUCCES_GOOGLE -> {
                view.showProgress(View.GONE)
                view.setDataProfile(event.any as User)
                view.visibleMenuOptions(View.VISIBLE)
                view.visibleLogin(View.GONE)
                view.setDataProfile(event.any as User)
            }
            MenusEvents.ON__SIGIN_ERROR -> {
                view.showProgress(View.GONE)
                view.visibleLogin(View.VISIBLE)
                view.showMessagge(event.any)
            }

            MenusEvents.ON_RECOVERY_SESSION_SUCCESS -> {
                view.showProgress(View.GONE)
                view.setDataProfile(event.any as User)
                view.visibleMenuOptions(View.VISIBLE)
                view.visibleLogin(View.GONE)
            }

            MenusEvents.ON_RECOVERY_SESSION_ERROR -> {
                //view.showProgress(View.GONE)
                //view.visibleMenuOptions(View.GONE)
                //view.visibleLogin(View.VISIBLE)
            }

        }
    }
}