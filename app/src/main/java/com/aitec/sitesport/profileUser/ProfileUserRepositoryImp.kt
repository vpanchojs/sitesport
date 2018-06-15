package com.aitec.sitesport.profileUser

import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.User
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.profileUser.events.ProfileUserEvents
import com.google.firebase.firestore.DocumentSnapshot

class ProfileUserRepositoryImp(var eventBus: EventBusInterface, var firebaseApi: FirebaseApi) : ProfileUserRepository {

    override fun getInfoUser() {
        firebaseApi.getInfoUser(object : onApiActionListener<DocumentSnapshot> {
            override fun onSucces(response: DocumentSnapshot) {
                val user = response.toObject(User::class.java)
                user!!.pk = response.id
                postEvent(ProfileUserEvents.ON_GET_PROFILE_SUCCESS, user)
            }

            override fun onError(error: Any?) {
                postEvent(ProfileUserEvents.ON_GET_PROFILE_ERROR, error)
            }
        });
    }

    override fun updateInfoUser(user: User) {
        firebaseApi.updateUser(user, object : onApiActionListener<Unit> {
            override fun onSucces(response: Unit) {
                postEvent(ProfileUserEvents.ON_UPDATE_PROFILE_SUCCESS, response)

            }

            override fun onError(error: Any?) {
                postEvent(ProfileUserEvents.ON_UPDATE_PROFILE_ERROR, error!!)
            }

        })
    }

    override fun updatePhotoUser(photo: String) {
        firebaseApi.updatePhoto(photo, object : onApiActionListener<String> {
            override fun onSucces(response: String) {
                postEvent(ProfileUserEvents.ON_UPDATE_PHOTO_USER_SUCCESS, response)
            }

            override fun onError(error: Any?) {
                postEvent(ProfileUserEvents.ON_UPDATE_PHOTO_USER_ERROR, error)
            }
        })
    }

    private fun postEvent(type: Int, any: Any?) {
        eventBus.post(ProfileUserEvents(type, any))
    }


}