package com.aitec.sitesport.sites.domain.di

import com.aitec.sitesport.MyAplicationModule
import com.aitec.sitesport.domain.di.DomainModule
import com.aitec.sitesport.lib.di.LibModule
import com.aitec.sitesport.sites.ui.SitesFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by victor on 21/12/17.
 */
@Singleton
@Component(modules = arrayOf(LibModule::class, SitesModule::class, MyAplicationModule::class, DomainModule::class))
interface SitesComponent {
    fun inject(sites: SitesFragment)

}