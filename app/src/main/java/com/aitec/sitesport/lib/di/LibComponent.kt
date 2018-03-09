package com.aitec.sitesport.lib.di

import com.aitec.sitesport.MyAplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf( LibModule::class, MyAplicationModule::class))
interface LibComponent {

}