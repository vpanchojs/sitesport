package com.aitec.sitesport.home.di

import com.aitec.sitesport.MyAplicationModule
import com.aitec.sitesport.domain.di.DomainModule
import com.aitec.sitesport.home.ui.HomeFragment
import com.aitec.sitesport.lib.di.LibModule
import com.aitec.sitesport.menu.di.MenusModule
import com.aitec.sitesport.sites.domain.di.SitesModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Jhony on 28 may 2018.
 */


@Singleton
@Component(modules = arrayOf(LibModule::class, HomeModule::class, MyAplicationModule::class, DomainModule::class))
interface HomeComponent {
    fun inject(homeFragment: HomeFragment)
}