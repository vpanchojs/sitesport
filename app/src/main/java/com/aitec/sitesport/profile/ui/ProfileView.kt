package com.aitec.sitesport.profile.ui

import android.graphics.Bitmap
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.entities.enterprise.Fotos
import com.aitec.sitesport.entities.enterprise.Precio
import com.aitec.sitesport.entities.TableTime
import com.mapbox.mapboxsdk.geometry.LatLng

/**
 * Created by Yavac on 12/3/2018.
 */
interface ProfileView {

    // Header
    /*fun setNameProfile(name : String?)
    fun setImageProfile(urls : List<Fotos>?)
    fun setLikes(likes : Int?)
    fun setupCollapsibleDynamic()

    fun setStateEnterprise(state : String?)
    fun setPriceDayStandard(priceDay : String?)
    fun setPriceNightStandard(priceNight : String?)
    fun setupMap(locationLatLng : LatLng?)*/

    //model
    fun setEnterprise(enterprise : Enterprise)

}