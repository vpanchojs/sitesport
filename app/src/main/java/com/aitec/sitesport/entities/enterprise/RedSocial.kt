package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Yavac on 22/3/2018.
 */
class RedSocial() : Parcelable{

    var facebook: String = ""
    var whatsapp: String = ""
    var instagram: String = ""

    constructor(parcel: Parcel) : this() {
        facebook = parcel.readString()
        whatsapp = parcel.readString()
        instagram = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(facebook)
        parcel.writeString(whatsapp)
        parcel.writeString(instagram)
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