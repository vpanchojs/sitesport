package com.aitec.sitesport.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.GeoPoint


class ParcelableGeoPoint : Parcelable {

    var geoPoint: GeoPoint

    constructor(point: GeoPoint) {
        geoPoint = point
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeDouble(geoPoint.latitude)
        out.writeDouble(geoPoint.longitude)
    }

    private constructor(parcel: Parcel) {
        val lat = parcel.readInt()
        val lon = parcel.readInt()
        geoPoint = GeoPoint(lat.toDouble(), lon.toDouble())
    }

    companion object {

        val CREATOR: Parcelable.Creator<ParcelableGeoPoint> = object : Parcelable.Creator<ParcelableGeoPoint> {
            override fun createFromParcel(parcel: Parcel): ParcelableGeoPoint {
                return ParcelableGeoPoint(parcel)
            }

            override fun newArray(size: Int): Array<ParcelableGeoPoint?> {
                return arrayOfNulls(size)
            }
        }
    }
}