package com.aitec.sitesport.menu.di

import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.menu.*
import com.aitec.sitesport.menu.ui.MenusView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MenusModule(var view: MenusView) {
    @Provides
    @Singleton
    fun providesMenuView(): MenusView {
        return view;
    }

    @Provides
    @Singleton
    fun providesMenusPresenter(eventBus: EventBusInterface, view: MenusView, interactor: MenusInteractor): MenusPresenter {
        return MenusPresenterImp(eventBus, view, interactor)
    }

    @Provides
    @Singleton
    fun providesMenusInteractor(repository: MenusRepository, eventBus: EventBusInterface): MenusInteractor {
        return MenusInteractorImp(repository, eventBus)
    }

    @Provides
    @Singleton
    fun providesMenusRepository(eventBus: EventBusInterface, sharePreferencesApi: SharePreferencesApi, firebaseApi: FirebaseApi): MenusRepository {
        return MenusRepositoryImp(eventBus, sharePreferencesApi, firebaseApi)
    }
}


