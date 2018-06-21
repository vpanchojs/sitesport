package com.aitec.sitesport.menu.di

import com.aitec.sitesport.MyAplicationModule
import com.aitec.sitesport.domain.di.DomainModule
import com.aitec.sitesport.lib.di.LibModule
import com.aitec.sitesport.menu.ui.MenuFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by victor on 27/1/18.
 */
@Singleton


@Component(modules = [LibModule::class, MenusModule::class, MyAplicationModule::class, DomainModule::class])
interface MenusComponent {
    fun inject(menuFragment: MenuFragment)
}