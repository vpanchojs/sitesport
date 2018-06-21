package com.aitec.sitesport.entities

import android.os.Parcel
import android.os.Parcelable

class Rate() : Parcelable {

    var nameDay: Int = 0
    var priceDay: String = ""
    var priceNight: String = ""
    var rankDay: String = ""
    var rankNight: String = ""

    constructor(parcel: Parcel) : this() {
        nameDay = parcel.readInt()
        priceDay = parcel.readString()
        priceNight = parcel.readString()
        rankDay = parcel.readString()
        rankNight = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(nameDay)
        parcel.writeString(priceDay)
        parcel.writeString(priceNight)
        parcel.writeString(rankDay)
        parcel.writeString(rankNight)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Rate> {
        override fun createFromParcel(parcel: Parcel): Rate {
            return Rate(parcel)
        }

        override fun newArray(size: Int): Array<Rate?> {
            return arrayOfNulls(size)
        }
    }
}