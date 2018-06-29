package com.aitec.sitesport.publication.di

import com.aitec.sitesport.MyAplicationModule
import com.aitec.sitesport.domain.di.DomainModule
import com.aitec.sitesport.lib.di.LibModule
import com.aitec.sitesport.publication.ui.PublicationActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(LibModule::class, PublicationModule::class, MyAplicationModule::class, DomainModule::class))
interface PublicationComponent {
    fun inject(activity: PublicationActivity)
}