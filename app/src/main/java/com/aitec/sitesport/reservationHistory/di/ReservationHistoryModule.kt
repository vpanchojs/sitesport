package com.aitec.sitesport.reservationHistory.di

import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.domain.SharePreferencesApi
import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.reservationHistory.*
import com.aitec.sitesport.reservationHistory.ui.ReservationHistoryView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ReservationHistoryModule(private val reservationHistoryView: ReservationHistoryView) {

    @Provides
    @Singleton
    fun providesReservationHistoryView() = reservationHistoryView

    @Provides
    @Singleton
    fun providesReservationHistoryPresenter(reservationHistoryView: ReservationHistoryView, reservationHistoryInteractor: ReservationHistoryInteractor, eventBusInterface: EventBusInterface)
            : ReservationHistoryPresenter = ReservationHistoryPresenterImpl(reservationHistoryView, reservationHistoryInteractor, eventBusInterface)


    @Provides
    @Singleton
    fun providesReservationHistoryInteractor(reservationHistoryRepository: ReservationHistoryRepository)
            : ReservationHistoryInteractor = ReservationHistoryInteractorImpl(reservationHistoryRepository)

    @Provides
    @Singleton
    fun providesReservationHistoryRepository(firebaseApi: FirebaseApi, eventBusInterface: EventBusInterface, sharePreferencesApi: SharePreferencesApi)
            : ReservationHistoryRepository = ReservationHistoryRepositoryImpl(firebaseApi, eventBusInterface, sharePreferencesApi)

}