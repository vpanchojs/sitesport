package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Yavac on 22/3/2018.
 */
class Fotos() : Parcelable{

    var imagen: String = ""

    constructor(parcel: Parcel) : this() {
        imagen = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imagen)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Fotos> {
        override fun createFromParcel(parcel: Parcel): Fotos {
            return Fotos(parcel)
        }

        override fun newArray(size: Int): Array<Fotos?> {
            return arrayOfNulls(size)
        }
    }

}