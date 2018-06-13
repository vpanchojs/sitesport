package com.aitec.sitesport.domain.di

import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.main.*
import com.aitec.sitesport.main.ui.MainView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainModule(var view: MainView) {

    @Provides
    @Singleton
    fun providesMainView(): MainView {
        return view;
    }

    @Provides
    @Singleton
    fun providesMainPresenter(eventBus: EventBusInterface, view: MainView, interactor: MainInteractor): MainPresenter {
        return MainPresenterImp(eventBus, view, interactor)
    }

    @Provides
    @Singleton
    fun providesMainInteractor(repository: MainRepository): MainInteractor {
        return MainInteractorImp(repository)
    }

    @Provides
    @Singleton
    fun providesMainRepository(eventBus: EventBusInterface, firebaseApi: FirebaseApi, sharePreferencesApi: SharePreferencesApi): MainRepository {
        return MainRepositoryImp(eventBus, firebaseApi, sharePreferencesApi)
    }

}