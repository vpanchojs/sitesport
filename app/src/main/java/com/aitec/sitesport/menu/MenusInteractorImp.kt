package com.aitec.sitesport.menu

/**
 * Created by victor on 27/1/18.
 */
class MenusInteractorImp(var repository: MenusRepository) : MenusInteractor {

    override fun enviartokengoogle(idToken: String) {
        repository.enviartokengoogle(idToken)
    }

    override fun enviartoken(token: String) {
        repository.enviartoken(token)
    }

    override fun onSingOut(platform: Int) {
        repository.onSingOut(platform)
    }

    override fun onUpdatePassword(password: String, passwordOld: String) {
        repository.onUpdatePassword(password, passwordOld)
    }

    override fun getMyProfile() {
        repository.getMyProfile()
    }
}