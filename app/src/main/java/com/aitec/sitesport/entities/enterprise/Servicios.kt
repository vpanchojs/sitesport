package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable

class Servicios() : Parcelable {


    var BAR: Boolean = false
    var WIFI: Boolean = false
    var PARKER: Boolean = false
    var DUCHA: Boolean = false
    var LOKER: Boolean = false

    constructor(parcel: Parcel) : this() {
        BAR = parcel.readByte() != 0.toByte()
        WIFI = parcel.readByte() != 0.toByte()
        PARKER = parcel.readByte() != 0.toByte()
        DUCHA = parcel.readByte() != 0.toByte()
        LOKER = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (BAR) 1 else 0)
        parcel.writeByte(if (WIFI) 1 else 0)
        parcel.writeByte(if (PARKER) 1 else 0)
        parcel.writeByte(if (DUCHA) 1 else 0)
        parcel.writeByte(if (LOKER) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Servicios> {
        override fun createFromParcel(parcel: Parcel): Servicios {
            return Servicios(parcel)
        }

        override fun newArray(size: Int): Array<Servicios?> {
            return arrayOfNulls(size)
        }
    }


}