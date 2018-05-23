package com.aitec.sitesport.domain

import android.util.Log
import com.aitec.sitesport.domain.db.SqliteDatabase
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.User
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SqliteRoomApi(var db: SqliteDatabase) {

    fun setUser(user: User, callback: onApiActionListener<Long>) {
        var id: Long = 0
        doAsync {

            var userBd = db.userDao().getUser(user.pk!!)

            if (userBd == null) {
                id = db.userDao().insertUser(user)
                Log.e("db", "se creo")
            } else {
                user.id = userBd.id
                db.userDao().updateUser(user)
                Log.e("db", "se actualizo")
            }
            uiThread {
                callback.onSucces(id)
            }
        }

    }
}