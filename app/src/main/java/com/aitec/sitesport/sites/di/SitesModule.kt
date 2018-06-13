package com.aitec.sitesport.sites.domain.di

import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.sites.*
import com.aitec.sitesport.sites.ui.SitesView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SitesModule(var view: SitesView) {

    @Provides
    @Singleton
    fun providesView(): SitesView {
        return view;
    }

    @Provides
    @Singleton
    fun providesPresenter(eventBus: EventBusInterface, view: SitesView, interactor: SitesInteractor): SitesPresenter {
        return SitesPresenterImp(eventBus, view, interactor)
    }

    @Provides
    @Singleton
    fun providesInteractor(repository: SitesRepository): SitesInteractor {
        return SitesInteractorImp(repository)
    }

    @Provides
    @Singleton
    fun providesRepository(eventBus: EventBusInterface, firebaseApi: FirebaseApi): SitesRepository {
        return SitesRepositoryImp(eventBus, firebaseApi)
    }

}