package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Yavac on 22/3/2018.
 */
class Precio() : Parcelable{
    var valor: String = ""
    var nombre: String = ""

    constructor(parcel: Parcel) : this() {
        valor = parcel.readString()
        nombre = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(valor)
        parcel.writeString(nombre)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Precio> {
        override fun createFromParcel(parcel: Parcel): Precio {
            return Precio(parcel)
        }

        override fun newArray(size: Int): Array<Precio?> {
            return arrayOfNulls(size)
        }
    }

}