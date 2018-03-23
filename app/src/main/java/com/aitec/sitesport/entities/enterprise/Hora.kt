package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Yavac on 22/3/2018.
 */
class Hora() : Parcelable{

    var hora_ini: String = ""
    var hora_fin: String = ""

    constructor(parcel: Parcel) : this() {
        hora_ini = parcel.readString()
        hora_fin = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(hora_ini)
        parcel.writeString(hora_fin)
    }

    override fun describeContents(): Int {
        return 0
    }


    companion object CREATOR : Parcelable.Creator<Hora> {
        override fun createFromParcel(parcel: Parcel): Hora {
            return Hora(parcel)
        }

        override fun newArray(size: Int): Array<Hora?> {
            return arrayOfNulls(size)
        }
    }

}