package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Yavac on 22/3/2018.
 */
class Telefonos() : Parcelable{

    var numero: String = ""
    var tipo: String = ""

    constructor(parcel: Parcel) : this() {
        numero = parcel.readString()
        tipo = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(numero)
        parcel.writeString(tipo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Telefonos> {
        override fun createFromParcel(parcel: Parcel): Telefonos {
            return Telefonos(parcel)
        }

        override fun newArray(size: Int): Array<Telefonos?> {
            return arrayOfNulls(size)
        }
    }

}