package com.aitec.sitesport.mapSites.di

import com.aitec.sitesport.MyAplicationModule
import com.aitec.sitesport.domain.di.DomainModule
import com.aitec.sitesport.domapSites.di.MapSitesModule
import com.aitec.sitesport.lib.di.LibModule
import com.aitec.sitesport.mapSites.ui.MapSitesActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by victor on 21/12/17.
 */
@Singleton
@Component(modules = arrayOf(LibModule::class, MapSitesModule::class, MyAplicationModule::class, DomainModule::class))
interface MapSitesComponent {
    fun inject(activity: MapSitesActivity)

}