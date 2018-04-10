package com.aitec.sitesport.domapSites.di

import com.aitec.sitesport.domain.RetrofitApi
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.mapSites.*
import com.aitec.sitesport.mapSites.ui.MapSitesView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MapSitesModule(var view: MapSitesView) {

    @Provides
    @Singleton
    fun providesmapSitesView(): MapSitesView {
        return view;
    }

    @Provides
    @Singleton
    fun providesmapSitesPresenter(eventBus: EventBusInterface, view: MapSitesView, interactor: MapSitesInteractor): MapSitesPresenter {
        return MapSitesPresenterImp(eventBus, view, interactor)
    }

    @Provides
    @Singleton
    fun providesmapSitesInteractor(repository: MapSitesRepository): MapSitesInteractor {
        return MapSitesInteractorImp(repository)
    }

    @Provides
    @Singleton
    fun providesmapSitesRepository(eventBus: EventBusInterface, retrofitApi: RetrofitApi, sharePreferencesApi: SharePreferencesApi): MapSitesRepository {
        return MapSitesRepositoryImp(eventBus, retrofitApi, sharePreferencesApi)
    }

}