package com.aitec.sitesport.domain.di

import com.aitec.sitesport.MyAplicationModule
import com.aitec.sitesport.lib.di.LibModule
import com.aitec.sitesport.main.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by victor on 21/12/17.
 */
@Singleton
@Component(modules = arrayOf(LibModule::class, MainModule::class, MyAplicationModule::class, DomainModule::class))
interface MainComponent {
    fun inject(activity: MainActivity)

}