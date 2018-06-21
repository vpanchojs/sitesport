package com.aitec.sitesport.domain.di

import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.domain.SharePreferencesApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.storage.FirebaseStorage
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
    fun providesFirebaseApi(): FirebaseApi {
        return FirebaseApi(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance(), FirebaseStorage.getInstance().reference, FirebaseFunctions.getInstance())
    }


}