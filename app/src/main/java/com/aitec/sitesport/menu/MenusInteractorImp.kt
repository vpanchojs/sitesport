package com.aitec.sitesport.menu

import android.net.Uri
import android.util.Log
import com.aitec.sitesport.entities.User
import com.aitec.sitesport.lib.base.EventBusInterface

/**
 * Created by victor on 27/1/18.
 */
class MenusInteractorImp(var repository: MenusRepository, eventBus: EventBusInterface) : MenusInteractor {

    override fun enviartokengoogle(idToken: String, name: String?, lastname: String?, email: String?, photoUrl: Uri?) {
        val user = User()
        user.foto = photoUrl.toString()
        user.apellido = lastname!!
        user.nombre = name!!
        user.correo_electronico = email!!
        repository.enviartokengoogle(idToken, user)
    }

    override fun enviartoken(token: String, name: String?, lastname: String?, email: String?, photoUrl: Uri?) {
        Log.e("INTERAC","iniciando con fb")
        val user = User()
        user.foto = photoUrl.toString()
        user.apellido = lastname!!
        user.nombre = name
        user.correo_electronico = email!!
        repository.enviartoken(token, user)
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