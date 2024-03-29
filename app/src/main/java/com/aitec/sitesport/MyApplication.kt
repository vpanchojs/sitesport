package com.aitec.sitesport

import android.content.Context
import android.content.SharedPreferences
import android.support.multidex.MultiDexApplication
import android.util.Log
import com.aitec.sitesport.domain.di.DaggerMainComponent
import com.aitec.sitesport.domain.di.DomainModule
import com.aitec.sitesport.domain.di.MainComponent
import com.aitec.sitesport.domain.di.MainModule
import com.aitec.sitesport.domapSites.di.MapSitesModule
import com.aitec.sitesport.home.di.DaggerHomeComponent
import com.aitec.sitesport.home.di.HomeComponent
import com.aitec.sitesport.home.di.HomeModule
import com.aitec.sitesport.home.ui.HomeView
import com.aitec.sitesport.lib.di.LibModule
import com.aitec.sitesport.main.ui.MainView
import com.aitec.sitesport.mapSites.di.DaggerMapSitesComponent
import com.aitec.sitesport.mapSites.di.MapSitesComponent
import com.aitec.sitesport.mapSites.ui.MapSitesView
import com.aitec.sitesport.menu.di.DaggerMenusComponent
import com.aitec.sitesport.menu.di.MenusComponent
import com.aitec.sitesport.menu.di.MenusModule
import com.aitec.sitesport.menu.ui.MenusView
import com.aitec.sitesport.profileEnterprise.di.DaggerProfileComponent
import com.aitec.sitesport.profileEnterprise.di.ProfileComponent
import com.aitec.sitesport.profileEnterprise.di.ProfileModule
import com.aitec.sitesport.profileEnterprise.ui.ProfileView
import com.aitec.sitesport.profileUser.di.DaggerProfileUserComponent
import com.aitec.sitesport.profileUser.di.ProfileUserComponent
import com.aitec.sitesport.profileUser.di.ProfileUserModule
import com.aitec.sitesport.profileUser.ui.ProfileUserView
import com.aitec.sitesport.publication.di.DaggerPublicationComponent
import com.aitec.sitesport.publication.di.PublicationComponent
import com.aitec.sitesport.publication.di.PublicationModule
import com.aitec.sitesport.publication.ui.PublicationView
import com.aitec.sitesport.reservationHistory.di.DaggerReservationHistoryComponent
import com.aitec.sitesport.reservationHistory.di.ReservationHistoryComponent
import com.aitec.sitesport.reservationHistory.di.ReservationHistoryModule
import com.aitec.sitesport.reservationHistory.ui.ReservationHistoryView
import com.aitec.sitesport.reserve.di.DaggerReserveComponent
import com.aitec.sitesport.reserve.di.ReserveComponent
import com.aitec.sitesport.reserve.di.ReserveModule
import com.aitec.sitesport.reserve.ui.ReserveView
import com.aitec.sitesport.sites.domain.di.DaggerSitesComponent
import com.aitec.sitesport.sites.domain.di.SitesComponent
import com.aitec.sitesport.sites.domain.di.SitesModule
import com.aitec.sitesport.sites.ui.SitesView

class MyApplication : MultiDexApplication() {
    val SHARED_PREFERENCES_NAME = "dsafio_preferences"
    var domainModule: DomainModule? = null
    var appModule: MyAplicationModule? = null


    override fun onCreate() {
        super.onCreate()
        Log.e("APLI", "on create")
        initModules();


    }

    fun getSharePreferences(): SharedPreferences {
        return getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun initModules() {
        appModule = MyAplicationModule(this)
        domainModule = DomainModule(this)

    }


    fun getSitesComponent(sitesView: SitesView): SitesComponent {
        return DaggerSitesComponent
                .builder()
                .domainModule(domainModule)
                .libModule(LibModule())
                .sitesModule(SitesModule(sitesView))
                .build()
    }

    fun getMenusComponent(menusView: MenusView): MenusComponent {
        return DaggerMenusComponent
                .builder()
                .domainModule(domainModule)
                .libModule(LibModule())
                .menusModule(MenusModule(menusView))
                .build()
    }

    fun getMainComponent(view: MainView): MainComponent {
        return DaggerMainComponent
                .builder()
                .domainModule(domainModule)
                .libModule(LibModule())
                .mainModule(MainModule(view))
                .build()
    }

    fun getMapSitesComponent(view: MapSitesView): MapSitesComponent {
        return DaggerMapSitesComponent
                .builder()
                .domainModule(domainModule)
                .libModule(LibModule())
                .mapSitesModule(MapSitesModule(view))
                .build()
    }


    fun getProfileComponent(profileView: ProfileView): ProfileComponent {
        return DaggerProfileComponent
                .builder()
                .domainModule(domainModule)
                .libModule(LibModule())
                .profileModule(ProfileModule(profileView))
                .build()
    }


    fun getProfileUserComponent(profileView: ProfileUserView): ProfileUserComponent {
        return DaggerProfileUserComponent
                .builder()
                .domainModule(domainModule)
                .libModule(LibModule())
                .profileUserModule(ProfileUserModule(profileView))
                .build()
    }

    fun getReserveComponent(view: ReserveView): ReserveComponent {
        return DaggerReserveComponent
                .builder()
                .domainModule(domainModule)
                .libModule(LibModule())
                .reserveModule(ReserveModule(view))
                .build()
    }

    fun getHomeComponent(view: HomeView): HomeComponent {
        return DaggerHomeComponent
                .builder()
                .domainModule(domainModule)
                .libModule(LibModule())
                .homeModule(HomeModule(view))
                .build()
    }

    fun getReservationHistoryComponent(view: ReservationHistoryView): ReservationHistoryComponent {
        return DaggerReservationHistoryComponent
                .builder()
                .domainModule(domainModule)
                .libModule(LibModule())
                .reservationHistoryModule(ReservationHistoryModule(view))
                .build()
    }

    fun getPublicationComponent(view: PublicationView): PublicationComponent {
        return DaggerPublicationComponent
                .builder()
                .domainModule(domainModule)
                .libModule(LibModule())
                .publicationModule(PublicationModule(view))
                .build()
    }


}