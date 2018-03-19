package com.aitec.sitesport.entities

import android.os.Parcel
import android.os.Parcelable

class Entreprise() : Parcelable {

    lateinit var pk: String
    lateinit var centro_deportivo: String
    var latitud: Double = 0.0
    var longitud: Double = 0.0
    var distancia: Double = 0.0
    var idMarker: Long = 0L

    constructor(parcel: Parcel) : this() {
        pk = parcel.readString()
        centro_deportivo = parcel.readString()
        latitud = parcel.readDouble()
        longitud = parcel.readDouble()
        distancia = parcel.readDouble()
        idMarker = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pk)
        parcel.writeString(centro_deportivo)
        parcel.writeDouble(latitud)
        parcel.writeDouble(longitud)
        parcel.writeDouble(distancia)
        parcel.writeLong(idMarker)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Entreprise> {
        override fun createFromParcel(parcel: Parcel): Entreprise {
            return Entreprise(parcel)
        }

        override fun newArray(size: Int): Array<Entreprise?> {
            return arrayOfNulls(size)
        }
    }

}
