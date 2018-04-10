package com.aitec.sitesport.entities

import android.os.Parcel
import android.os.Parcelable

class Address() : Parcelable {
    var longitud: Double = 0.0
    lateinit var referencia: String
    lateinit var provincia: String
    lateinit var ciudad: String
    lateinit var pais: String
    var latitud: Double = 0.0
    lateinit var calles: String
    var distance: Double = 0.0

    constructor(parcel: Parcel) : this() {
        longitud = parcel.readDouble()
        referencia = parcel.readString()
        provincia = parcel.readString()
        ciudad = parcel.readString()
        pais = parcel.readString()
        latitud = parcel.readDouble()
        calles = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(longitud)
        parcel.writeString(referencia)
        parcel.writeString(provincia)
        parcel.writeString(ciudad)
        parcel.writeString(pais)
        parcel.writeDouble(latitud)
        parcel.writeString(calles)
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