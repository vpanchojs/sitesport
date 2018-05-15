package com.aitec.sitesport.reserve.di

import com.aitec.sitesport.MyAplicationModule
import com.aitec.sitesport.domain.di.DomainModule
import com.aitec.sitesport.lib.di.LibModule
import com.aitec.sitesport.reserve.ui.ReserveActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by victor on 27/1/18.
 */
@Singleton
@Component(modules = [LibModule::class, ReserveModule::class, MyAplicationModule::class, DomainModule::class])
interface ReserveComponent {
    fun inject(activity: ReserveActivity)
}