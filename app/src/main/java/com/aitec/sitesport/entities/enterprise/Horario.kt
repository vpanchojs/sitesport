package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Yavac on 23/3/2018.
 */
class Horario() : Parcelable{

    var nombre : String = ""

    constructor(parcel: Parcel) : this() {
        nombre = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Horario> {
        override fun createFromParcel(parcel: Parcel): Horario {
            return Horario(parcel)
        }

        override fun newArray(size: Int): Array<Horario?> {
            return arrayOfNulls(size)
        }
    }

}