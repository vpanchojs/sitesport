package com.aitec.sitesport.reserve.di

import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.reserve.*
import com.aitec.sitesport.reserve.ui.ReserveView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ReserveModule(var view: ReserveView) {

    @Provides
    @Singleton
    fun providesView(): ReserveView {
        return view;
    }

    @Provides
    @Singleton
    fun providesReservePresenter(eventBus: EventBusInterface, view: ReserveView, interactor: ReserveInteractor): ReservePresenter {
        return ReservePresenterImp(eventBus, view, interactor)
    }

    @Provides
    @Singleton
    fun providesReserveInteractor(repository: ReserveRepository, eventBus: EventBusInterface): ReserveInteractor {
        return ReserveInteractorImp(repository, eventBus)
    }

    @Provides
    @Singleton
    fun providesReserveRepository(eventBus: EventBusInterface, firebaseApi: FirebaseApi): ReserveRepository {
        return ReserveRepositoryImp(eventBus, firebaseApi)
    }
}


