package com.aitec.sitesport.profile

import com.aitec.sitesport.domain.RetrofitApi
import com.aitec.sitesport.lib.base.EventBusInterface

/**
 * Created by Yavac on 16/3/2018.
 */
class ProfileRepositoryImpl(var retrofitApi: RetrofitApi, var eventBusInterface : EventBusInterface) : ProfileRepository{

    override fun stopRequests() {
    }

    override fun getProfile(pk: String) {
        retrofitApi.getProfile(pk)
    }


}