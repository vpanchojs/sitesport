package com.aitec.sitesport.profileEnterprise.di

import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.profileEnterprise.*
import com.aitec.sitesport.profileEnterprise.ui.ProfileView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Yavac on 16/3/2018.
 */
@Module
class ProfileModule(private val profileView: ProfileView) {

    @Provides
    @Singleton
    fun providesProfileView() = profileView

    @Provides
    @Singleton
    fun providesProfilePresenter(profileView: ProfileView, profileInteractor: ProfileInteractor, eventBusInterface: EventBusInterface)
            : ProfilePresenter = ProfilePresenterImpl(profileView, profileInteractor, eventBusInterface)


    @Provides
    @Singleton
    fun providesProfileInteractor(profileRepository: ProfileRepository)
            : ProfileInteractor = ProfileInteractorImpl(profileRepository)

    @Provides
    @Singleton
    fun providesProfileRepository(firebaseApi: FirebaseApi, eventBusInterface: EventBusInterface, sharePreferencesApi: SharePreferencesApi)
            : ProfileRepository = ProfileRepositoryImpl(firebaseApi, eventBusInterface, sharePreferencesApi)

}