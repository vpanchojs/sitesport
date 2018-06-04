package com.aitec.sitesport.menu

import com.aitec.sitesport.lib.base.EventBusInterface

/**
 * Created by victor on 27/1/18.
 */
class MenusInteractorImp(var repository: MenusRepository, eventBus: EventBusInterface) : MenusInteractor {

    override fun enviartokengoogle(idToken: String) {
        repository.enviartokengoogle(idToken)
    }

    override fun enviartoken(token: String) {
        repository.enviartoken(token)
    }

    override fun onSingOut() {
        repository.onSingOut()
    }

    override fun subscribeAuth() {
        repository.subscribeAuth()
    }

    override fun unSubscribeAuth() {
        repository.unSubscribeAuth()
    }

    override fun onUpdatePassword(password: String, passwordOld: String) {
        repository.onUpdatePassword(password, passwordOld)
    }

    override fun getMyProfile() {
        repository.getMyProfile()
    }

}