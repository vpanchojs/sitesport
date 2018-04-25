package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Yavac on 22/3/2018.
 */
class Telefono() : Parcelable{

    var celular : String = ""
    var convencional : String = ""

    constructor(parcel: Parcel) : this() {
        celular = parcel.readString()
        convencional = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(celular)
        parcel.writeString(convencional)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Telefono> {
        override fun createFromParcel(parcel: Parcel): Telefono {
            return Telefono(parcel)
        }

        override fun newArray(size: Int): Array<Telefono?> {
            return arrayOfNulls(size)
        }
    }

}