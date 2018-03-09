package com.aitec.sitesport.domain.di

import android.util.Log
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.domain.VolleyApi
import com.aitec.sitesport.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DomainModule(var app: MyApplication) {

    @Provides
    @Singleton
    fun providesVolleyApi(): VolleyApi {
        Log.e("BOX", "creando volley")
        return VolleyApi(app)
    }


    @Provides
    @Singleton
    fun providesSharePreferences(): SharePreferencesApi {
        return SharePreferencesApi(app.getSharePreferences())
    }


}