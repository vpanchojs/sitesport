package com.aitec.sitesport.publication.di

import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.publication.*
import com.aitec.sitesport.publication.ui.PublicationView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PublicationModule(private val publicationView: PublicationView) {

    @Provides
    @Singleton
    fun providesPublicationView() = publicationView

    @Provides
    @Singleton
    fun providesPublicationPresenter(publicationView: PublicationView, publicationInteractor: PublicationInteractor, eventBusInterface: EventBusInterface)
            : PublicationPresenter = PublicationPresenterImpl(publicationView, publicationInteractor, eventBusInterface)


    @Provides
    @Singleton
    fun providesPublicationInteractor(publicationRepository: PublicationRepository)
            : PublicationInteractor = PublicationInteractorImpl(publicationRepository)

    @Provides
    @Singleton
    fun providesPublicationRepository(firebaseApi: FirebaseApi, eventBusInterface: EventBusInterface, sharePreferencesApi: SharePreferencesApi)
            : PublicationRepository = PublicationRepositoryImpl(firebaseApi, eventBusInterface, sharePreferencesApi)

}