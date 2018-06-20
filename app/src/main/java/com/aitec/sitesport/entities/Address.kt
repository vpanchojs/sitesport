package com.aitec.sitesport.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.GeoPoint

class Address() : Parcelable {

    lateinit var referencia: String
    lateinit var provincia: String
    lateinit var ciudad: String
    lateinit var pais: String
    var calles: String = ""
    var latitud: Double = 0.0
    var longitud: Double = 0.0

    //var gPointParcelable : ParcelableGeoPoint = ParcelableGeoPoint(GeoPoint(-4.034063, -79.202306))


    constructor(parcel: Parcel) : this() {
        referencia = parcel.readString()
        provincia = parcel.readString()
        ciudad = parcel.readString()
        pais = parcel.readString()
        calles = parcel.readString()
        latitud = parcel.readDouble()
        longitud = parcel.readDouble()

        //gPointParcelable = parcel.readParcelable(ParcelableGeoPoint::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(referencia)
        parcel.writeString(provincia)
        parcel.writeString(ciudad)
        parcel.writeString(pais)
        parcel.writeString(calles)
        parcel.writeDouble(latitud)
        parcel.writeDouble(longitud)

        //parcel.writeParcelable(gPointParcelable, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Address> {
        override fun createFromParcel(parcel: Parcel): Address {
            return Address(parcel)
        }

        override fun newArray(size: Int): Array<Address?> {
            return arrayOfNulls(size)
        }
    }

}