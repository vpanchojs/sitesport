package com.aitec.sitesport.domain

import android.content.SharedPreferences
import com.aitec.sitesport.domain.listeners.onApiActionListener

/**
 * Created by victor on 21/1/18.
 */
class SharePreferencesApi(var sharedPreferences: SharedPreferences) {
    val KEY_TOKEN_ACCESS = "97e64d26-3b04-417a-8ee2-e761c04b855b"
    val KEY_TOKEN_NOTIFICATION = "74864c0b-076a-4044-85b8-af834f1b53f7"
    val KEY_IN_SESESION = "12be06b6-e0e6-45af-a800-82e4273736df"
    val KEY_PLATFORM = "12be06b6-e0e6-45af-a800-af834f1b53f7"
    val PK_ENTERPRISE = "pk_enterprise"
    val KEY_FIRST_RUN = "first_run"


    fun saveTokenAndSession(tokenAccess: String) {
        sharedPreferences.edit().putString(KEY_TOKEN_ACCESS, tokenAccess).commit()
    }

    fun getTokenAccess(): String {
        return sharedPreferences.getString(KEY_TOKEN_ACCESS, "")
    }

    fun getInSession(): Boolean {
        return sharedPreferences.getBoolean(KEY_IN_SESESION, false)
    }

    fun savePkEnterprise(pk: String) {
        sharedPreferences.edit().putString(PK_ENTERPRISE, pk).commit()
    }

    fun getPkEnterprise(): String {
        return sharedPreferences.getString(PK_ENTERPRISE, "")
    }

    fun getFirstRun(callback: onApiActionListener<Any>) {
        val firts = sharedPreferences.getBoolean(KEY_FIRST_RUN, true)
        if (firts) {
            sharedPreferences.edit().putBoolean(KEY_FIRST_RUN, false).commit()
            callback.onSucces(Any())
        }

    }

    fun sesion(session: Boolean, platform: Int) {
        sharedPreferences.edit().putBoolean(KEY_IN_SESESION, session).commit()
        sharedPreferences.edit().putInt(KEY_PLATFORM, platform).commit()
    }

}