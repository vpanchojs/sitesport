package com.aitec.sitesport.domain.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import com.aitec.sitesport.entities.User

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User): Long
}