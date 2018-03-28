package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Yavac on 22/3/2018.
 */
class Hora() : Parcelable{

    var inicio: String = ""
    var fin: String = ""

    constructor(parcel: Parcel) : this() {
        inicio = parcel.readString()
        fin = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(inicio)
        parcel.writeString(fin)
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