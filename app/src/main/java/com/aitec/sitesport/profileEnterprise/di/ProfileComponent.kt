package com.aitec.sitesport.profileEnterprise.di

import com.aitec.sitesport.MyAplicationModule
import com.aitec.sitesport.domain.di.DomainModule
import com.aitec.sitesport.lib.di.LibModule
import com.aitec.sitesport.profileEnterprise.ui.ProfileActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Yavac on 16/3/2018.
 */
@Singleton
@Component(modules = arrayOf(LibModule::class, ProfileModule::class, MyAplicationModule::class, DomainModule::class))
interface ProfileComponent {
    fun inject(activity: ProfileActivity)
}