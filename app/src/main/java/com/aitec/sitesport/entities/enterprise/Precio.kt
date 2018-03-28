package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Yavac on 22/3/2018.
 */
class Precio() : Parcelable{
    var noche: String = ""
    var dia: String = ""

    constructor(parcel: Parcel) : this() {
        noche = parcel.readString()
        dia = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(noche)
        parcel.writeString(dia)
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