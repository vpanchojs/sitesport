package com.aitec.sitesport.profile.di

import com.aitec.sitesport.domain.RetrofitApi
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.profile.*
import com.aitec.sitesport.profile.ui.ProfileView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Yavac on 16/3/2018.
 */
@Module
class ProfileModule(private val profileView : ProfileView){

    @Provides
    @Singleton
    fun providesProfileView() = profileView

    @Provides
    @Singleton
    fun providesProfilePresenter(profileView : ProfileView, profileInteractor : ProfileInteractor, eventBusInterface : EventBusInterface)
            : ProfilePresenter = ProfilePresenterImpl(profileView, profileInteractor, eventBusInterface)


    @Provides
    @Singleton
    fun providesProfileInteractor(profileRepository : ProfileRepository)
            : ProfileInteractor = ProfileInteractorImpl(profileRepository)

    @Provides
    @Singleton
    fun providesProfileRepository(retrofitApi: RetrofitApi, eventBusInterface : EventBusInterface, sharePreferencesApi: SharePreferencesApi)
            : ProfileRepository = ProfileRepositoryImpl(retrofitApi, eventBusInterface, sharePreferencesApi)

}