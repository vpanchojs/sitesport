package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Yavac on 22/3/2018.
 */
class RedSocial() : Parcelable{

    var nombre: String = ""
    var url: String = ""

    constructor(parcel: Parcel) : this() {
        nombre = parcel.readString()
        url = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RedSocial> {
        override fun createFromParcel(parcel: Parcel): RedSocial {
            return RedSocial(parcel)
        }

        override fun newArray(size: Int): Array<RedSocial?> {
            return arrayOfNulls(size)
        }
    }


}