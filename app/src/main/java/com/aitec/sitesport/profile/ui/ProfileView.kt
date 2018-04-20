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
    fun setEnterprise(enterprise: Enterprise)
    fun setLikes(likes: Int)
    fun setImages(imagesUrls: List<Fotos>)
    fun setStateEnterprise(state: Boolean)
    fun setPriceDayStandard(priceDay: String?)
    fun setPriceNightStandard(priceNight: String?)
}