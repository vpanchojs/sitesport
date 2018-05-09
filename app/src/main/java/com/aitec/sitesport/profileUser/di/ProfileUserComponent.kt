package com.aitec.sitesport.profileUser.di

import com.aitec.sitesport.MyAplicationModule
import com.aitec.sitesport.domain.di.DomainModule
import com.aitec.sitesport.lib.di.LibModule
import com.aitec.sitesport.profileUser.ui.ProfileUserActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by victor on 21/12/17.
 */
@Singleton
@Component(modules = arrayOf(LibModule::class, ProfileUserModule::class, MyAplicationModule::class, DomainModule::class))
interface ProfileUserComponent {
    fun inject(activity: ProfileUserActivity)

}