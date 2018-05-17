package com.aitec.sitesport.menu.di

import com.aitec.sitesport.domain.RetrofitApi
import com.aitec.sitesport.menu.*
import com.aitec.sitesport.menu.ui.MenusView
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.lib.base.EventBusInterface
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
    fun providesMenusInteractor(repository: MenusRepository): MenusInteractor {
        return MenusInteractorImp(repository)
    }

    @Provides
    @Singleton
    fun providesMenusRepository(eventBus: EventBusInterface, sharePreferencesApi: SharePreferencesApi, retrofitApi: RetrofitApi): MenusRepository {
        return MenusRepositoryImp(eventBus, sharePreferencesApi,retrofitApi)
    }
}


