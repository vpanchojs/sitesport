package com.aitec.sitesport.home.di

import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.domain.RetrofitApi
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.domain.SqliteRoomApi
import com.aitec.sitesport.home.*
import com.aitec.sitesport.home.ui.HomeView
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.sites.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Jhony on 28 may 2018.
 */
@Module
class HomeModule (var view: HomeView) {


    @Provides
    @Singleton
    fun providesView(): HomeView {
        return view;
    }

    @Provides
    @Singleton
    fun providesHomePresenter(eventBus: EventBusInterface, view: HomeView, interactor: HomeInteractor): HomePresenter {
        return HomePresenterImp(eventBus, view, interactor)

    }

    @Provides
    @Singleton
    fun providesHomeInteractor(repository: HomeRepository): HomeInteractor {
        return HomeInteractorImp(repository)
    }

    @Provides
    @Singleton
    fun providesHomeRepository(eventBus: EventBusInterface, sharePreferencesApi: SharePreferencesApi, retrofitApi: RetrofitApi, sqliteRoomApi: SqliteRoomApi, firebaseApi: FirebaseApi): HomeRepository {
        return HomeRepositoryImp(eventBus, sharePreferencesApi, retrofitApi, sqliteRoomApi,firebaseApi)
    }
}