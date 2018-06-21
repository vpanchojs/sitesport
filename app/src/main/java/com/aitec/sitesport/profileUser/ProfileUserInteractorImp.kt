package com.aitec.sitesport.profileUser

import com.aitec.sitesport.entities.User
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.profileUser.events.ProfileUserEvents
import com.aitec.sitesport.util.BaseActivitys

class ProfileUserInteractorImp(var eventBus: EventBusInterface, var repository: ProfileUserRepository) : ProfileUserInteractor {


    override fun getInfoUser() {
        repository.getInfoUser()
    }


    override fun updateInfoUser(names: String, lastnames: String, dni: String, phone: String, photo: String) {
        var correct = true

        if (!BaseActivitys.validaDni(dni)) {
            postEvent(ProfileUserEvents.DNI_INVALID, "Número de identificacion inválido")
            correct = false
        }

        if (!(phone.length == 10)) {
            postEvent(ProfileUserEvents.PHONE_INVALID, "Número de celular inválido")
            correct = false
        }


        if (correct) {
            var user = User()
            user.cedula = dni
            user.nombre = names
            user.apellido = lastnames
            user.telefono = phone
            user.foto = photo
            repository.updateInfoUser(user)
        }

    }

    override fun updatePhotoUser(photo: String) {
        repository.updatePhotoUser(photo)
    }

    private fun postEvent(type: Int, any: Any) {
        var event = ProfileUserEvents(type, any)
        eventBus.post(event)
    }


}
