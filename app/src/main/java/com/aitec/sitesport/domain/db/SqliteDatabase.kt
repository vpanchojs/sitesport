package com.aitec.sitesport.domain.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.aitec.sitesport.domain.db.UserDao
import com.aitec.sitesport.entities.User

@Database(entities = arrayOf(User::class), version = 1)
abstract class SqliteDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}