package com.aitec.sitesport

import android.content.Context
import android.content.SharedPreferences
import android.support.multidex.MultiDexApplication
import android.util.Log
import com.aitec.sitesport.domain.di.DaggerMainComponent
import com.aitec.sitesport.domain.di.DomainModule
import com.aitec.sitesport.domain.di.MainComponent
import com.aitec.sitesport.domain.di.MainModule
import com.aitec.sitesport.lib.di.LibModule
import com.aitec.sitesport.main.ui.MainView
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

    fun getMenusComponent(menusView: MenusView): MenusComponent {
        return DaggerMenusComponent
                .builder()
                .domainModule(domainModule)
                .libModule(LibModule())
                .menusModule(MenusModule(menusView))
                .build()
    }
*/
    fun getMainComponent(view: MainView): MainComponent {
        return DaggerMainComponent
                .builder()
                .domainModule(domainModule)
                .libModule(LibModule())
                .mainModule(MainModule(view))
                .build()
    }



}