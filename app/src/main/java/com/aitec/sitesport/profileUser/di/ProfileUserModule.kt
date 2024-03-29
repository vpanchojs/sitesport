package com.aitec.sitesport.profileUser.di

import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.profileUser.*
import com.aitec.sitesport.profileUser.ui.ProfileUserView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProfileUserModule(var view: ProfileUserView) {

    @Provides
    @Singleton
    fun providesProfileUserView(): ProfileUserView {
        return view;
    }

    @Provides
    @Singleton
    fun providesProfileUserPresenter(eventBus: EventBusInterface, view: ProfileUserView, interactor: ProfileUserInteractor): ProfileUserPresenter {
        return ProfileUserPresenterImp(eventBus, view, interactor)
    }

    @Provides
    @Singleton
    fun providesProfileUserInteractor(eventBus: EventBusInterface, repository: ProfileUserRepository): ProfileUserInteractor {
        return ProfileUserInteractorImp(eventBus, repository)
    }

    @Provides
    @Singleton
    fun providesProfileUserRepository(eventBus: EventBusInterface, firebaseApi: FirebaseApi): ProfileUserRepository {
        return ProfileUserRepositoryImp(eventBus, firebaseApi)
    }

}