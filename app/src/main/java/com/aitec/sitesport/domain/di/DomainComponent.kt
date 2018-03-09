package com.aitec.sitesport.domain.di


import com.aitec.sitesport.MyAplicationModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by victor on 21/12/17.
 */
@Singleton
@Component(modules = [DomainModule::class, MyAplicationModule::class])
interface DomainComponent {

}