package com.aitec.sitesport.profile.ui

import android.graphics.Bitmap
import com.aitec.sitesport.entities.TableTime
import com.mapbox.mapboxsdk.geometry.LatLng
import org.json.JSONObject

/**
 * Created by Yavac on 12/3/2018.
 */
interface ProfileView {

    // Header
    fun setImageProfile(image : Bitmap)
    fun setNameProfile(name : String)
    fun setQualificationProfile(qualification : Float)
    fun setVisits(visits : String)
    fun setupCollapsibleDynamic()

    // content
    fun setTableTime(times : TableTime)
    fun setPriceHourDay(prices : JSONObject)
    fun setPriceHourNight(prices : JSONObject)
    fun setPhoneNumber(phoneNumber: String)
    fun setWhatsAppNumber(whatAppNumber : String)
    fun setFacebookUser(facebookUser : String)
    fun setLatLngLocationMap(locationLatLng : LatLng)
    fun setMarkerLocationMap(marker : Bitmap)

}