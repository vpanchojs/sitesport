package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable

class Servicio() : Parcelable {

    var nombre: String = ""
    var descripcion: String = ""
    var activado: Boolean = false

    constructor(parcel: Parcel) : this() {
        nombre = parcel.readString()
        descripcion = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(descripcion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Servicio> {
        override fun createFromParcel(parcel: Parcel): Servicio {
            return Servicio(parcel)
        }

        override fun newArray(size: Int): Array<Servicio?> {
            return arrayOfNulls(size)
        }
    }

}