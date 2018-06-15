package com.aitec.sitesport.profileUser

import com.aitec.sitesport.entities.User

interface ProfileUserRepository {
    fun getInfoUser()
    fun updateInfoUser(user: User)
    fun updatePhotoUser(photo: String)
}
