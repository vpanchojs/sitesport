package com.aitec.sitesport.profile

import android.util.Log
import com.aitec.sitesport.domain.RetrofitApi
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.profile.event.ProfileEvent
import com.google.gson.Gson


/**
 * Created by Yavac on 16/3/2018.
 */
class ProfileRepositoryImpl(var retrofitApi: RetrofitApi,
                            var eventBusInterface : EventBusInterface,
                            var sharePreferencesApi: SharePreferencesApi) : ProfileRepository{


    override fun stopRequests() {
    }

    override fun getProfile(urlDetail : String) {
        retrofitApi.getProfile(urlDetail, object : onApiActionListener {
            override fun onSucces(response: Any?) {
                postEvent(ProfileEvent.SUCCESS_PROFILE, "",
                        Gson().fromJson(response.toString(), Enterprise::class.java))
                Log.e("getProfile:onSucces()", Gson().fromJson(response.toString(), Enterprise::class.java).toString())
            }

            override fun onError(error: Any?) {
                postEvent(ProfileEvent.ERROR_PROFILE, error.toString(), null)
                Log.e("getProfile:onError()", error.toString())
            }
        })
    }

    private fun saveIdEnterprise(pk : String){
        sharePreferencesApi.savePkEnterprise(pk)
    }

    private fun postEvent(type: Int, any: Any, enterprise : Enterprise?) {
        val event = ProfileEvent(type, any.toString(), enterprise)
        eventBusInterface.post(event)
    }

}