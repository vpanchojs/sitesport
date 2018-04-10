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
import com.aitec.sitesport.lib.di.LibModule
import com.aitec.sitesport.main.ui.MainView
import com.aitec.sitesport.mapSites.di.DaggerMapSitesComponent
import com.aitec.sitesport.mapSites.di.MapSitesComponent
import com.aitec.sitesport.mapSites.ui.MapSitesView
import com.aitec.sitesport.menu.di.DaggerMenusComponent
import com.aitec.sitesport.menu.di.MenusComponent
import com.aitec.sitesport.menu.di.MenusModule
import com.aitec.sitesport.menu.ui.MenusView
import com.aitec.sitesport.sites.domain.di.DaggerSitesComponent
import com.aitec.sitesport.sites.domain.di.SitesComponent
import com.aitec.sitesport.sites.domain.di.SitesModule
import com.aitec.sitesport.sites.ui.SitesView
import com.mapbox.mapboxsdk.Mapbox

class MyApplication : MultiDexApplication() {
    val SHARED_PREFERENCES_NAME = "dsafio_preferences"
    var domainModule: DomainModule? = null
    var appModule: MyAplicationModule? = null
    lateinit var mapbox: Mapbox

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
        mapbox = Mapbox.getInstance(this, getString(R.string.accessTokenMapBox))
    }


    /*
    fun getLoginComponent(view: LoginView): LoginComponent {
        return DaggerLoginComponent.builder()
                .domainModule(domainModule)
                .loginModule(LoginModule(view))
                .build();
    }

    fun getSignupUserInfoComponent(signupUserInfoView: SignupUserInfoView): SignupUserInfoComponent {
        return DaggerSignupUserInfoComponent
                .builder()
                .domainModule(domainModule)
                .libModule(LibModule())
                .signupUserInfoModule(SignupUserInfoModule(signupUserInfoView))
                .build()
    }

    fun getSignupAcctInfoComponent(signupAcctInfoView: SignupAcctInfoView): SignupAcctInfoComponent {
        return DaggerSignupAcctInfoComponent
                .builder()
                .domainModule(domainModule)
                .libModule(LibModule())
                .signupAcctInfoModule(SignupAcctInfoModule(signupAcctInfoView))
                .build()
    }
*/
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



    /*
    fun getProfileComponent(profileView: ProfileView): ProfileComponent {
        return DaggerProfileComponent
                .builder()
                .domainModule(domainModule)
                .libModule(LibModule())
                .profileModule(ProfileModule(profileView))
                .build()
    }
*/

}