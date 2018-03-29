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
    fun setImageProfile(urls : List<Fotos>?)
    fun setNameProfile(name : String?)
    fun setQualificationProfile(qualification : Float?)
    fun setVisits(visits : String?)
    fun setupCollapsibleDynamic()

    // content
    /*fun setTableTime(times : TableTime)
    fun setPriceHourDay(prices : List<Precio>)
    fun setPriceHourNight(prices : List<Precio>)
    fun setPhoneNumber(phonesNumber: ArrayList<String>)
    fun setWhatsAppNumber(whatAppNumber : String)
    fun setFacebookUser(facebookUser : String)*/
    fun setLatLngLocationMap(locationLatLng : LatLng?)
    fun setMarkerLocationMap(marker : Bitmap?)
    fun setStateEnterprise(state : String?)
    fun setPriceDayStandar(priceDay : String?)
    fun setPriceNightStandar(priceNight : String?)
    fun showProgressContent()
    fun hideProgressContent()
    fun showContentLoading()
    fun hideContentLoading()
    fun showTextInfoLoading()
    fun hideTextInfoLoading()
    fun setTextInfoLoading(msg : String)

    //model
    fun setEnterprise(enterprise : Enterprise)

}