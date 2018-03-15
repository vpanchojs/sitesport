package com.aitec.sitesport.domain.di

import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.domain.RetrofitApi
import com.aitec.sitesport.domain.SharePreferencesApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DomainModule(var app: MyApplication) {

    @Provides
    @Singleton
    fun providesSharePreferences(): SharePreferencesApi {
        return SharePreferencesApi(app.getSharePreferences())
    }

    @Provides
    @Singleton
    fun providesRetrofitApi(): RetrofitApi {
        return RetrofitApi()
    }


}