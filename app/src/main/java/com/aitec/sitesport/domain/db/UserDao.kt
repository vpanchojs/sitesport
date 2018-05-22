package com.aitec.sitesport.domain.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.aitec.sitesport.entities.User

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User): Long

    @Query("Select *FROM user where pk=:pk")
    fun getUser(pk: String): User

    @Update
    fun updateUser(user: User): Int
}