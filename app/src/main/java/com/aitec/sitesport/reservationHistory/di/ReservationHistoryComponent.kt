package com.aitec.sitesport.reservationHistory.di

import com.aitec.sitesport.MyAplicationModule
import com.aitec.sitesport.domain.di.DomainModule
import com.aitec.sitesport.lib.di.LibModule
import com.aitec.sitesport.reservationHistory.ui.ReservationHistoryFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(LibModule::class, ReservationHistoryModule::class, MyAplicationModule::class, DomainModule::class))
interface ReservationHistoryComponent {
    fun inject(fragment: ReservationHistoryFragment)
}